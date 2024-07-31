package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private long reviewId;

    @Column(name = "keyword_id", nullable = false)
    private long keywordId;

    public ReviewKeyword(long reviewId, long keywordId) {
        this.reviewId = reviewId;
        this.keywordId = keywordId;
    }
}
