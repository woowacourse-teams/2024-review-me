package reviewme.review.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checkbox_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class CheckboxAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false, insertable = false, updatable = false)
    private long reviewId;

    @Column(name = "question_id", nullable = false)
    private long questionId;

    @ElementCollection
    @CollectionTable(
            name = "checkbox_answer_selected_option",
            joinColumns = @JoinColumn(name = "checkbox_answer_id", nullable = false)
    )
    @Column(name = "selected_option_id", nullable = false)
    private List<Long> selectedOptionIds;

    public CheckboxAnswer(long questionId, List<Long> selectedOptionIds) {
        this.questionId = questionId;
        this.selectedOptionIds = selectedOptionIds;
    }
}
