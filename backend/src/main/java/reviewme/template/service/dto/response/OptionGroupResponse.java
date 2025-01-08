package reviewme.template.service.dto.response;

import java.util.List;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionGroupSelectionCount;

public record OptionGroupResponse(
        long optionGroupId,
        int minCount,
        int maxCount,
        List<OptionItemResponse> options
) {
    public static OptionGroupResponse from(OptionGroup optionGroup) {
        List<OptionItemResponse> optionItemResponses = optionGroup.getOptionItems()
                .stream()
                .map(OptionItemResponse::from)
                .toList();

        OptionGroupSelectionCount selectionCount = optionGroup.getSelectionCount();

        return new OptionGroupResponse(
                optionGroup.getId(),
                selectionCount.getMinSelectionCount(),
                selectionCount.getMaxSelectionCount(),
                optionItemResponses
        );
    }
}
