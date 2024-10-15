package reviewme.highlight.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.HighlightedLine;
import reviewme.highlight.entity.Highlight;
import reviewme.highlight.domain.HighlightLines;
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
        Map<Long, HighlightLines> answerHighlightLines = textAnswerRepository
                .findAllById(highlightsRequest.getUniqueAnswerIds())
                .stream()
                .collect(Collectors.toMap(Answer::getId, answer -> new HighlightLines(answer.getContent())));
        setIndexRanges(highlightsRequest, answerHighlightLines);
        return  mapLinesToHighlights(answerHighlightLines);
    }

    private void setIndexRanges(HighlightsRequest highlightsRequest, Map<Long, HighlightLines> answerHighlightLines) {
        for (HighlightRequest highlightRequest : highlightsRequest.highlights()) {
            HighlightLines highlightLines = answerHighlightLines.get(highlightRequest.answerId());
            setIndexRangesForAnswer(highlightRequest, highlightLines);
        }
    }

    private void setIndexRangesForAnswer(HighlightRequest highlightRequest, HighlightLines highlightLines) {
        for (HighlightedLineRequest lineRequest : highlightRequest.lines()) {
            int lineIndex = lineRequest.index();
            for (HighlightIndexRangeRequest rangeRequest : lineRequest.ranges()) {
                highlightLines.setRange(lineIndex, rangeRequest.startIndex(), rangeRequest.endIndex());
            }
        }
    }

    private List<Highlight> mapLinesToHighlights(Map<Long, HighlightLines> answerHighlightLines) {
        List<Highlight> highlights = new ArrayList<>();
        for (Entry<Long, HighlightLines> answerHighlightLine : answerHighlightLines.entrySet()) {
            createHighlightsForAnswer(answerHighlightLine, highlights);
        }
        return highlights;
    }

    private void createHighlightsForAnswer(Entry<Long, HighlightLines> answerHighlightLine, List<Highlight> highlights) {
        long answerId = answerHighlightLine.getKey();
        HighlightLines highlightLines = answerHighlightLine.getValue();
        for (HighlightedLine line : highlightLines.getLines()) {
            for (HighlightRange range : line.getRanges()) {
                Highlight highlight = new Highlight(answerId, line.getLineIndex(), range);
                highlights.add(highlight);
            }
        }
    }
}
