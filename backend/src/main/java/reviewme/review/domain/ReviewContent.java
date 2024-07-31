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

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "answer", nullable = false, length = MAX_ANSWER_LENGTH)
    private String answer;

    public ReviewContent(long questionId, String answer) {
        validateAnswerLength(answer);
        this.questionId = questionId;
        this.answer = answer;
    }

    private void validateAnswerLength(String answer) {
        if (answer.length() < MIN_ANSWER_LENGTH || answer.length() > MAX_ANSWER_LENGTH) {
            throw new InvalidAnswerLengthException(MIN_ANSWER_LENGTH, MAX_ANSWER_LENGTH);
        }
    }
}
