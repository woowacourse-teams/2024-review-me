package reviewme.review.service.dto.response.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "옵션 그룹 정보")
public record OptionGroupAnswerResponse(

        @Schema(description = "옵션 그룹 ID")
        long optionGroupId,

        @Schema(description = "최소 선택 수")
        long minCount,

        @Schema(description = "최대 선택 수")
        long maxCount,

        @Schema(description = "옵션 목록")
        List<OptionItemAnswerResponse> options
) {
}
