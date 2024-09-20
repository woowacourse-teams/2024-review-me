package reviewme.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.review.domain.exception.QuestionNotAnsweredException;

@Entity
@Table(name = "text_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
public class TextAnswer extends Answer {

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
