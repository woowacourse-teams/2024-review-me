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
import reviewme.review.service.CreateTextAnswerRequestValidator;

@Entity
@Table(name = "text_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class TextAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "content", nullable = false)
    private String content;

    public TextAnswer(long questionId, String content) {
        this.questionId = questionId;
        this.content = content;
    }
}
