package reviewme.template.domain.structured;

import java.util.List;
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

    public List<Long> getOptionGroupIds() {
        return optionItems.stream()
                .map(OptionItem::getOptionGroupId)
                .toList();
    }
}
