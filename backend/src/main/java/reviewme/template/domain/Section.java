package reviewme.template.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visible_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibleType visibleType;

    @ElementCollection
    @CollectionTable(name = "question_ids", joinColumns = @JoinColumn(name = "section_id"))
    private List<Long> questionIds;

    @Column(name = "on_selected_option_id", nullable = true)
    private Long onSelectedOptionId;

    @Column(name = "header", nullable = false, length = 1_000)
    private String header;

    @Column(name = "position", nullable = false)
    private int position;

    public Section(VisibleType visibleType, List<Long> questionIds,
                   Long onSelectedOptionId, String header, int position) {
        this.visibleType = visibleType;
        this.questionIds = questionIds;
        this.onSelectedOptionId = onSelectedOptionId;
        this.header = header;
        this.position = position;
    }
}
