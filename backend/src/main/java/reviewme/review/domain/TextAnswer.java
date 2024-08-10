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
@Table(name = "text_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TextAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "text", nullable = false, length = 1_000)
    private String text;

    public TextAnswer(long questionId, String text) {
        this.questionId = questionId;
        this.text = text;
    }
}
