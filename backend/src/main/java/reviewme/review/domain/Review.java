package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;

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
    @JoinColumn(name = "reviewer_group_id", nullable = false)
    private ReviewerGroup reviewerGroup;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review(Member reviewer, ReviewerGroup reviewerGroup, LocalDateTime createdAt) {
        this.reviewer = reviewer;
        this.reviewerGroup = reviewerGroup;
        this.createdAt = createdAt;
    }
}
