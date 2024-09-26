package reviewme.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visible_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibleType visibleType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "section_id", nullable = false, updatable = false)
    private List<SectionQuestion> questionIds;

    @Column(name = "on_selected_option_id", nullable = true)
    private Long onSelectedOptionId;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "header", nullable = false, length = 1_000)
    private String header;

    @Column(name = "position", nullable = false)
    private int position;

    public Section(VisibleType visibleType, List<Long> questionIds,
                   Long onSelectedOptionId, String sectionName, String header, int position) {
        this.visibleType = visibleType;
        this.questionIds = questionIds.stream()
                .map(SectionQuestion::new)
                .toList();
        this.onSelectedOptionId = onSelectedOptionId;
        this.sectionName = sectionName;
        this.header = header;
        this.position = position;
    }

    public boolean isVisibleBySelectedOptionIds(Collection<Long> selectedOptionIds) {
        return visibleType == VisibleType.ALWAYS || selectedOptionIds.contains(onSelectedOptionId);
    }

    public boolean containsQuestionId(long questionId) {
        return questionIds.stream()
                .anyMatch(sectionQuestion -> sectionQuestion.hasQuestionId(questionId));
    }
}
