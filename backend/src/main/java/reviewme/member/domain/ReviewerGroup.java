package reviewme.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.member.domain.exception.InvalidDescriptionLengthException;
import reviewme.member.domain.exception.InvalidGroupNameLengthException;
import reviewme.member.domain.exception.SelfReviewException;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.DeadlineExpiredException;
import reviewme.review.domain.exception.RevieweeMismatchException;
import reviewme.review.exception.GithubReviewerGroupUnAuthorizedException;
import reviewme.review.exception.ReviewAlreadySubmittedException;

@Entity
@Table(name = "reviewer_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewerGroup {

    private static final Duration DEADLINE_DURATION = Duration.ofDays(7);
    private static final int MAX_DESCRIPTION_LENGTH = 50;
    private static final int MAX_GROUP_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ReviewerGroupGithubIds reviewerGithubIds;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private Member reviewee;

    @OneToMany(mappedBy = "reviewerGroup")
    private List<Review> reviews;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    public ReviewerGroup(Member reviewee, List<GithubId> reviewerGithubIds,
                         String groupName, String description, LocalDateTime deadline) {
        if (groupName.isBlank() || groupName.length() > MAX_GROUP_NAME_LENGTH) {
            throw new InvalidGroupNameLengthException(MAX_GROUP_NAME_LENGTH);
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidDescriptionLengthException(MAX_DESCRIPTION_LENGTH);
        }
        if (reviewerGithubIds.contains(reviewee.getGithubId())) {
            throw new SelfReviewException();
        }
        this.reviewee = reviewee;
        this.reviewerGithubIds = new ReviewerGroupGithubIds(this, reviewerGithubIds);
        this.groupName = groupName;
        this.description = description;
        this.deadline = deadline;
        this.reviews = new ArrayList<>();
        this.thumbnailUrl = "https://github.com/octocat.png";
    }

    public boolean isDeadlineExceeded(LocalDateTime now) {
        return now.isAfter(deadline);
    }

    public void addReview(Review review) {
        Member reviewer = review.getReviewer();
        if (isDeadlineExceeded(review.getCreatedAt())) {
            throw new DeadlineExpiredException();
        }
        if (hasSubmittedReviewBy(reviewer)) {
            throw new ReviewAlreadySubmittedException();
        }
        if (reviewerGithubIds.doesNotContain(reviewer)) {
            throw new GithubReviewerGroupUnAuthorizedException();
        }
        if (!review.getReviewee().equals(reviewee)) {
            throw new RevieweeMismatchException();
        }
        reviews.add(review);
    }

    private boolean hasSubmittedReviewBy(Member reviewer) {
        return reviews.stream()
                .anyMatch(review -> review.isSubmittedBy(reviewer));
    }

    public void addReviewerGithubId(GithubIdReviewerGroup githubIdReviewerGroup) {
        reviewerGithubIds.add(githubIdReviewerGroup);
    }
}
