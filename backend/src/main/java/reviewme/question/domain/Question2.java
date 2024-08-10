package reviewme.question.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Question2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "question_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(name = "content", nullable = false, length = 1_000)
    private String content;

    @Column(name = "guideline", nullable = true, length = 1_000)
    private String guideline;

    @Column(name = "position", nullable = false)
    private int position;

    public Question2(boolean required, QuestionType questionType, String content, String guideline, int position) {
        this.required = required;
        this.questionType = questionType;
        this.content = content;
        this.guideline = guideline;
        this.position = position;
    }

    public boolean isSelectable() {
        return questionType == QuestionType.CHECKBOX;
    }

    public boolean hasGuideline() {
        return guideline != null;
    }
}
