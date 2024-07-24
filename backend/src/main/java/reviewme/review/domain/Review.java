package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.Keywords;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.review.domain.exception.IllegalReviewerException;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Member reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private Member reviewee;

    @ManyToOne
    @JoinColumn(name = "reviewer_group_id", nullable = false)
    private ReviewerGroup reviewerGroup;

    @Embedded
    private Keywords keywords;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review(Member reviewer, Member reviewee, ReviewerGroup reviewerGroup,
                  List<Keyword> keywords, LocalDateTime createdAt) {
        if (reviewer.equals(reviewee)) {
            throw new IllegalReviewerException();
        }
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.reviewerGroup = reviewerGroup;
        this.keywords = new Keywords(keywords);
        this.createdAt = createdAt;
    }
}
