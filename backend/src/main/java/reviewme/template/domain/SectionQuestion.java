package reviewme.template.domain;

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
@Table(name = "section_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SectionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_id", nullable = false, insertable = false, updatable = false)
    private long sectionId;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    public SectionQuestion(long questionId) {
        this.questionId = questionId;
    }

    public boolean hasQuestionId(long questionId) {
        return this.questionId == questionId;
    }
}
