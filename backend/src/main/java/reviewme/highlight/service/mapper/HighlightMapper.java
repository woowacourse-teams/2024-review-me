package reviewme.highlight.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightLine;
import reviewme.highlight.domain.HighlightLines;
import reviewme.highlight.domain.HighlightRange;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.review.domain.Answer;
import reviewme.review.domain.TextAnswer;

@Component
@RequiredArgsConstructor
public class HighlightMapper {

    public List<Highlight> mapToHighlights(HighlightsRequest highlightsRequest, List<TextAnswer> textAnswers) {
        Map<Long, HighlightLines> answerHighlightLines = textAnswers.stream()
                .collect(Collectors.toMap(Answer::getId, answer -> new HighlightLines(answer.getContent())));

        for (HighlightRequest highlightRequest : highlightsRequest.highlights()) {
            HighlightLines highlightLines = answerHighlightLines.get(highlightRequest.answerId());
            addLine(highlightRequest, highlightLines);
        }

        List<Highlight> highlights = new ArrayList<>();
        for (Entry<Long, HighlightLines> answerHighlightLine : answerHighlightLines.entrySet()) {
            addHighlight(answerHighlightLine, highlights);
        }
        return highlights;
    }

    private void addLine(HighlightRequest highlightRequest, HighlightLines highlightLines) {
        for (HighlightedLineRequest lineRequest : highlightRequest.lines()) {
            int lineIndex = lineRequest.index();
            for (HighlightIndexRangeRequest rangeRequest : lineRequest.ranges()) {
                highlightLines.addRange(lineIndex, rangeRequest.startIndex(), rangeRequest.endIndex());
            }
        }
    }

    private void addHighlight(Entry<Long, HighlightLines> answerHighlightLine, List<Highlight> highlights) {
        long answerId = answerHighlightLine.getKey();
        HighlightLines highlightLines = answerHighlightLine.getValue();

        for (HighlightLine line : highlightLines.getLines()) {
            for (HighlightRange range : line.getRanges()) {
                Highlight highlight = new Highlight(answerId, line.getLineIndex(), range);
                highlights.add(highlight);
            }
        }
    }
}
