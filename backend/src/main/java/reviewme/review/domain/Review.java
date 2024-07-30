package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", nullable = false)
    private long groupId;

    @OneToMany
    @JoinColumn(name = "review_id", nullable = false)
    private List<ReviewContent> reviewContents;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review(long groupId, List<ReviewContent> reviewContents, LocalDateTime createdAt) {
        this.groupId = groupId;
        this.reviewContents = reviewContents;
        this.createdAt = createdAt;
    }

//    public boolean isSubmittedBy(Member member) {
//        return reviewer.equals(member);
//    }
//
//    public boolean isForReviewee(Member member) {
//        return reviewee.equals(member);
//    }
//
//    public void addReviewContents(ReviewContent reviewContent) {
//        reviewContents.add(reviewContent);
//    }
}
