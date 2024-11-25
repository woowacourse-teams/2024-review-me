package reviewme.highlight.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightedLines;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.TextAnswerRepository;

@Component
@RequiredArgsConstructor
public class HighlightMapper {

    private final TextAnswerRepository textAnswerRepository;

    public List<Highlight> mapToHighlights(HighlightsRequest highlightsRequest) {
        Map<Long, HighlightedLines> answerIdHighlightedLines = textAnswerRepository
                .findAllById(highlightsRequest.getUniqueAnswerIds())
                .stream()
                .collect(Collectors.toMap(TextAnswer::getId, answer -> new HighlightedLines(answer.getContent())));

        for (HighlightFragment fragment : highlightsRequest.toFragments()) {
            HighlightedLines highlightedLines = answerIdHighlightedLines.get(fragment.answerId());
            highlightedLines.addRange(fragment.lineIndex(), fragment.startIndex(), fragment.endIndex());
        }

        return answerIdHighlightedLines.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().toHighlights(entry.getKey()).stream())
                .toList();
    }
}
