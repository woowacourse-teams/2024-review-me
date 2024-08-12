package reviewme.template.dto.response;

import java.util.List;

public record OptionGroupResponse(

        long optionGroupId,
        int minCount,
        int maxCount,
        List<OptionItemResponse> options
) {
}
