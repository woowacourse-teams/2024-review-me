package reviewme.review.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<ReviewContent> reviewContents;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Review(long reviewGroupId, List<ReviewContent> reviewContents, LocalDateTime createdAt) {
        this.reviewGroupId = reviewGroupId;
        this.reviewContents = reviewContents;
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedDate() {
        return createdAt.toLocalDate();
    }
}
