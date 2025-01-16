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
import jakarta.persistence.ManyToOne;
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
    private List<Question> questions;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "on_selected_option_id", nullable = true)
    private OptionItem onSelectedOption;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @Column(name = "header", nullable = false, length = 1_000)
    private String header;

    @Column(name = "position", nullable = false)
    private int position;

    public Section(VisibleType visibleType, List<Question> questions,
                   OptionItem onSelectedOption, String sectionName, String header, int position) {
        if (questions.isEmpty()) {
            throw new IllegalArgumentException("질문은 최소 한 개 이상이어야 합니다.");
        }
        if (visibleType == VisibleType.CONDITIONAL && onSelectedOption == null) {
            throw new IllegalArgumentException("조건부 표시인 경우 선택 옵션이 필수입니다.");
        }
        this.visibleType = visibleType;
        this.questions = questions;
        this.onSelectedOption = onSelectedOption;
        this.sectionName = sectionName;
        this.header = header;
        this.position = position;
    }

    public boolean isVisibleBySelectedOptionIds(Collection<Long> selectedOptionIds) {
        return visibleType == VisibleType.ALWAYS || selectedOptionIds.contains(onSelectedOption.getId());
    }

    public boolean contains(Question question) {
        return questions.contains(question);
    }

    public boolean isConditional() {
        return visibleType == VisibleType.CONDITIONAL;
    }
}
