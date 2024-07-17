package reviewme.review.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.keyword.domain.Keyword;

@Entity
@Table(name = "review_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public ReviewKeyword(Review review, Keyword keyword) {
        this.review = review;
        this.keyword = keyword;
    }
}
