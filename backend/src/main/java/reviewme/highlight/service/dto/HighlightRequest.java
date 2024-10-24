package reviewme.highlight.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record HighlightRequest(

        @NotNull(message = "답변 ID를 입력해주세요.")
        Long answerId,

        @Valid @NotEmpty(message = "하이라이트 된 라인을 입력해주세요.")
        List<HighlightedLineRequest> lines
) {
}
