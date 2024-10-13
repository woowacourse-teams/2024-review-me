package reviewme.highlight.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record HighlightsRequest(

        @NotNull(message = "질문 ID를 입력해주세요.")
        Long questionId,

        @Valid @NotNull(message = "하이라이트할 부분을 입력해주세요.")
        List<HighlightRequest> highlights
) {
        public Set<Long> getUniqueAnswerIds() {
                return highlights()
                        .stream()
                        .map(HighlightRequest::answerId)
                        .collect(Collectors.toSet());
        }
}
