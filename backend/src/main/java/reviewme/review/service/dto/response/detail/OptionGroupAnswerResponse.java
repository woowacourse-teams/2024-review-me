package reviewme.review.service.dto.response.detail;

import java.util.List;

public record OptionGroupAnswerResponse(
        long optionGroupId,
        long minCount,
        long maxCount,
        List<OptionItemAnswerResponse> options
) {
}
