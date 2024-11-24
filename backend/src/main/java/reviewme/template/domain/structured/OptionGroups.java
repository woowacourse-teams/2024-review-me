package reviewme.template.domain.structured;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.exception.OptionGroupNotExistException;

@Getter
public class OptionGroups {

    private final List<OptionGroup> optionGroups;

    public OptionGroups(List<OptionGroup> optionGroups) {
        validateOptionGroups(optionGroups);
        this.optionGroups = optionGroups.stream()
                .distinct()
                .toList();
    }

    private void validateOptionGroups(List<OptionGroup> optionGroups) {
        if (optionGroups == null || optionGroups.isEmpty()) {
            throw new OptionGroupNotExistException();
        }
    }

    public List<Long> getOptionGroupIds() {
        return optionGroups.stream()
                .map(OptionGroup::getId)
                .toList();
    }

    public Set<Long> getQuestionIds() {
        return optionGroups.stream()
                .map(OptionGroup::getQuestionId)
                .collect(Collectors.toSet());
    }

    public int size() {
        return optionGroups.size();
    }
}
