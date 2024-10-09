package reviewme.highlight.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record HighlightedLineRequest(

        @NotNull(message = "인덱스를 입력해주세요.")
        Long index,

        @Valid @NotNull(message = "하이라이트 범위를 입력해주세요.")
        List<HighlightIndexRangeRequest> ranges
) {
}
