package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.review.domain.exception.InvalidAnswerLengthException;

@Entity
@Table(name = "review_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewContent {

    private static final int MIN_ANSWER_LENGTH = 20;
    private static final int MAX_ANSWER_LENGTH = 1_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer", nullable = false)
    private String answer;

    public ReviewContent(Review review, Question question, String answer) {
        validateAnswerLength(answer);
        this.review = review;
        this.question = question;
        this.answer = answer;
    }

    private void validateAnswerLength(String answer) {
        if (answer.length() < MIN_ANSWER_LENGTH || answer.length() > MAX_ANSWER_LENGTH) {
            throw new InvalidAnswerLengthException(MIN_ANSWER_LENGTH, MAX_ANSWER_LENGTH);
        }
    }

    public String getQuestion() {
        return question.getContent();
    }
}
