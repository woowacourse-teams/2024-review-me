package reviewme.template.service.dto.response;

import java.util.List;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionGroupSelectionRange;

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

        OptionGroupSelectionRange selectionRange = optionGroup.getSelectionRange();

        return new OptionGroupResponse(
                optionGroup.getId(),
                selectionRange.getMinSelectionCount(),
                selectionRange.getMaxSelectionCount(),
                optionItemResponses
        );
    }
}
