package reviewme.highlight.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;
import reviewme.highlight.service.mapper.HighlightFragment;

public record HighlightRequest(

        @NotNull(message = "답변 ID를 입력해주세요.")
        Long answerId,

        @Valid @NotEmpty(message = "하이라이트 된 라인을 입력해주세요.")
        List<HighlightedLineRequest> lines
) {
    public List<HighlightFragment> toFragments() {
        return lines.stream()
                .flatMap(this::mapRangesToFragment)
                .toList();
    }

    private Stream<HighlightFragment> mapRangesToFragment(HighlightedLineRequest line) {
        return line.ranges()
                .stream()
                .map(range -> new HighlightFragment(answerId, line.index(), range.startIndex(), range.endIndex()));
    }
}
