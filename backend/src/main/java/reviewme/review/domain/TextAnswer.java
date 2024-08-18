package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;

@Entity
@Table(name = "text_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class TextAnswer {

    public static final int MIN_LENGTH = 20;
    public static final int MAX_LENGTH = 1_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "content", nullable = false, length = MAX_LENGTH)
    private String content;

    public TextAnswer(long questionId, String content) {
        validateLength(content);
        this.questionId = questionId;
        this.content = content;
    }

    private void validateLength(String content) {
        if (content.length() < MIN_LENGTH || content.length() > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(content.length(), MIN_LENGTH, MAX_LENGTH);
        }
    }
}
