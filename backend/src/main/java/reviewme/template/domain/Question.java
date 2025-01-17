package reviewme.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "question_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private OptionGroup optionGroup;

    @Column(name = "content", nullable = false, length = 1_000)
    private String content;

    @Column(name = "guideline", nullable = true, length = 1_000)
    private String guideline;

    @Column(name = "position", nullable = false)
    private int position;

    // 질문 타입에 따른 Factory가 필요할 수 있다. Checkbox인 경우 OptionGroup을 가지게 하고, Text인 경우 그렇지 않고...
    // Required도 마찬가지로 Factory에서 설정해준다면 content, guideline, position과 같은 필수적인 정보만 생성자에 넣어주면 된다.
    // 사실 Position도 List의 순서에 따라 자동으로 배정하면 좋겠다. 같은 Section 안에 같은 position을 가질 수 없다는 불변식이 깨질 위험이 존재한다.
    // TODO: @OrderColumn을 사용해 Position 사용하지 않고 자동 설정
    // TODO: QuestionType에 따른 검증 로직 추가
    public Question(boolean required, QuestionType questionType, OptionGroup optionGroup,
                    String content, String guideline, int position) {
        this.required = required;
        this.questionType = questionType;
        this.optionGroup = optionGroup;
        this.content = content;
        this.guideline = guideline;
        this.position = position;
    }

    public Question(boolean required, QuestionType questionType, String content, String guideline, int position) {
        this(required, questionType, null, content, guideline, position);
    }

    public boolean isCheckbox() {
        return questionType == QuestionType.CHECKBOX;
    }

    public boolean hasGuideline() {
        return guideline != null && !guideline.isEmpty();
    }
}
