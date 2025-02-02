package reviewme.template.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "option_group_id", nullable = false, updatable = false)
    private List<OptionItem> optionItems;

    @Embedded
    private SelectionRange selectionRange;

    public OptionGroup(List<OptionItem> optionItems, int minSelectionCount, int maxSelectionCount) {
        if (optionItems.isEmpty()) {
            throw new EmptyOptionGroupException();
        }
        this.optionItems = optionItems;
        this.selectionRange = new SelectionRange(minSelectionCount, maxSelectionCount);
        if (optionItems.size() < maxSelectionCount) {
            throw new InvalidSelectionRangeException(optionItems.size(), minSelectionCount, maxSelectionCount);
        }
    }
}
