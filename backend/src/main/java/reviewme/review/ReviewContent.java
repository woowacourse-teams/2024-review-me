package reviewme.review;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "review_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    public ReviewContent(Review review, String question, String answer) {
        this.review = review;
        this.question = question;
        this.answer = answer;
    }
}
