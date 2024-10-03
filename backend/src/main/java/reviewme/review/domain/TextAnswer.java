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
import reviewme.review.domain.exception.QuestionNotAnsweredException;

@Entity
@Table(name = "text_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class TextAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false, insertable = false, updatable = false)
    private long reviewId;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "content", nullable = false, length = 5000)
    private String content;

    public TextAnswer(long questionId, String content) {
        validateContent(questionId, content);
        this.questionId = questionId;
        this.content = content;
    }

    private void validateContent(long questionId, String content) {
        if (content == null || content.isEmpty()) {
            throw new QuestionNotAnsweredException(questionId);
        }
    }
}
