package reviewme.template.domain;

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

    @OneToMany(mappedBy = "optionGroupId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "option_group_id", nullable = false, updatable = false)
    private List<OptionItem> optionItems;

    @Column(name = "min_selection_count", nullable = false)
    private int minSelectionCount;

    @Column(name = "max_selection_count", nullable = false)
    private int maxSelectionCount;

    public OptionGroup(List<OptionItem> optionItems, int minSelectionCount, int maxSelectionCount) {
        if (optionItems.isEmpty()) {
            throw new IllegalArgumentException("옵션 아이템은 최소 한 개 이상이어야 합니다.");
        }
        if (minSelectionCount < 0 || maxSelectionCount < 0 || minSelectionCount > maxSelectionCount) {
            throw new IllegalArgumentException("선택 가능한 아이템의 개수가 올바르지 않습니다.");
        }
        if (optionItems.size() < minSelectionCount || optionItems.size() > maxSelectionCount) {
            throw new IllegalArgumentException("선택 가능한 아이템의 개수가 올바르지 않습니다.");
        }
        this.optionItems = optionItems;
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }
}
