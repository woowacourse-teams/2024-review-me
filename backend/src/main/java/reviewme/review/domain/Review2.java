package reviewme.review.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review2")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Review2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id", nullable = false)
    private long templateId;

    @Column(name = "review_group_id", nullable = false)
    private long reviewGroupId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<TextAnswer> textAnswers;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private List<CheckboxAnswer> checkboxAnswers;

    public Review2(long templateId, long reviewGroupId,
                   List<TextAnswer> textAnswers, List<CheckboxAnswer> checkboxAnswers) {
        this.templateId = templateId;
        this.reviewGroupId = reviewGroupId;
        this.textAnswers = textAnswers;
        this.checkboxAnswers = checkboxAnswers;
    }

    public Map<Long, TextAnswer> getTextAnswers() {
        return textAnswers.stream()
                .collect(Collectors.toMap(TextAnswer::getQuestionId, Function.identity()));
    }
}
