package reviewme.template.domain.structured;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.exception.OptionItemNotExistException;

public class OptionItems {

    private final List<OptionItem> optionItems;

    public OptionItems(List<OptionItem> optionItems) {
        validateOptionItems(optionItems);
        this.optionItems = optionItems.stream()
                .distinct()
                .toList();
    }

    private void validateOptionItems(List<OptionItem> optionItems) {
        if (optionItems == null || optionItems.isEmpty()) {
            throw new OptionItemNotExistException();
        }
    }

    public List<Long> getOptionItemIds() {
        return optionItems.stream()
                .map(OptionItem::getId)
                .toList();
    }

    public Set<Long> getOptionGroupIds() {
        return optionItems.stream()
                .map(OptionItem::getOptionGroupId)
                .distinct()
                .collect(Collectors.toSet());
    }
}
