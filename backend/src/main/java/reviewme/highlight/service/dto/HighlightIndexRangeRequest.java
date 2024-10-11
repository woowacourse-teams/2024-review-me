package reviewme.highlight.service.dto;

import jakarta.validation.constraints.NotNull;

public record HighlightIndexRangeRequest(

        @NotNull(message = "시작 인덱스를 입력해주세요.")
        Integer startIndex,

        @NotNull(message = "끝 인덱스를 입력해주세요.")
        Integer endIndex
) {
}
