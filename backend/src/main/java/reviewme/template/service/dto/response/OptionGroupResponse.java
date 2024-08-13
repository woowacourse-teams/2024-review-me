package reviewme.template.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "옵션 그룹 응답")
public record OptionGroupResponse(

        @Schema(description = "옵션 그룹 ID")
        long optionGroupId,

        @Schema(description = "최소 선택 수")
        int minCount,

        @Schema(description = "최대 선택 수")
        int maxCount,

        @Schema(description = "옵션 목록")
        List<OptionItemResponse> options
) {
}
