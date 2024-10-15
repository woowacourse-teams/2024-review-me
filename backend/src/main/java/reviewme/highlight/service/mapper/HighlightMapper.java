package reviewme.highlight.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.HighlightedLines;
import reviewme.highlight.domain.HighlightedLine;
import reviewme.highlight.entity.Highlight;
import reviewme.highlight.entity.HighlightRange;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.review.domain.Answer;
import reviewme.review.repository.TextAnswerRepository;

@Component
@RequiredArgsConstructor
public class HighlightMapper {

    private final TextAnswerRepository textAnswerRepository;

    public List<Highlight> mapToHighlights(HighlightsRequest highlightsRequest) {
        Map<Long, HighlightedLines> answerHighlightLines = textAnswerRepository
                .findAllById(highlightsRequest.getUniqueAnswerIds())
                .stream()
                .collect(Collectors.toMap(Answer::getId, answer -> new HighlightedLines(answer.getContent())));
        addIndexRanges(highlightsRequest, answerHighlightLines);
        return mapLinesToHighlights(answerHighlightLines);
    }

    private void addIndexRanges(HighlightsRequest highlightsRequest, Map<Long, HighlightedLines> answerHighlightLines) {
        for (HighlightRequest highlightRequest : highlightsRequest.highlights()) {
            HighlightedLines highlightedLines = answerHighlightLines.get(highlightRequest.answerId());
            addIndexRangesForAnswer(highlightRequest, highlightedLines);
        }
    }

    private void addIndexRangesForAnswer(HighlightRequest highlightRequest, HighlightedLines highlightedLines) {
        for (HighlightedLineRequest lineRequest : highlightRequest.lines()) {
            int lineIndex = lineRequest.index();
            for (HighlightIndexRangeRequest rangeRequest : lineRequest.ranges()) {
                highlightedLines.addRange(lineIndex, rangeRequest.startIndex(), rangeRequest.endIndex());
            }
        }
    }

    private List<Highlight> mapLinesToHighlights(Map<Long, HighlightedLines> answerHighlightLines) {
        List<Highlight> highlights = new ArrayList<>();
        for (Entry<Long, HighlightedLines> answerHighlightLine : answerHighlightLines.entrySet()) {
            createHighlightsForAnswer(answerHighlightLine, highlights);
        }
        return highlights;
    }

    private void createHighlightsForAnswer(Entry<Long, HighlightedLines> answerHighlightLine,
                                           List<Highlight> highlights) {
        long answerId = answerHighlightLine.getKey();
        List<HighlightedLine> highlightedLines = answerHighlightLine.getValue().getLines();

        for (int lineIndex = 0; lineIndex < highlightedLines.size(); lineIndex++) {
            createHighlightForLine(highlightedLines, lineIndex, answerId, highlights);
        }
    }

    private void createHighlightForLine(List<HighlightedLine> highlightedLines, int lineIndex, long answerId,
                                        List<Highlight> highlights) {
        for (HighlightRange range : highlightedLines.get(lineIndex).getRanges()) {
            Highlight highlight = new Highlight(answerId, lineIndex, range);
            highlights.add(highlight);
        }
    }
}
