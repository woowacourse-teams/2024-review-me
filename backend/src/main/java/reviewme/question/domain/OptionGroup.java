package reviewme.question.domain;

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

@Entity
@Table(name = "option_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @Column(name = "min_selection_count", nullable = false)
    private int minSelectionCount;

    @Column(name = "max_selection_count", nullable = false)
    private int maxSelectionCount;

    public OptionGroup(long questionId, int minSelectionCount, int maxSelectionCount) {
        this.questionId = questionId;
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }
}
