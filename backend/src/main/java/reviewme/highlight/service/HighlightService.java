package reviewme.highlight.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightLines;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.validator.HighlightValidator;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.TextAnswers;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final TextAnswerRepository textAnswerRepository;

    private final HighlightValidator highlightValidator;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest, String reviewRequestCode) {
        long reviewGroupId = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode))
                .getId();
        List<TextAnswer> textAnswersByIds = textAnswerRepository.findAllById(highlightsRequest.getUniqueAnswerIds());
        TextAnswers textAnswers = new TextAnswers(textAnswersByIds);

        highlightValidator.validate(highlightsRequest, reviewGroupId);
        Map<Long, HighlightLines> highlightLines = mapHighlightLines(highlightsRequest, textAnswers);
        deleteBeforeHighlight(highlightsRequest.questionId(), textAnswers);
        saveHighlight(highlightLines);
    }

    private Map<Long, HighlightLines> mapHighlightLines(HighlightsRequest highlightsRequest, TextAnswers textAnswers) {
        Map<Long, List<Integer>> answerLineIndexes = highlightsRequest.highlights()
                .stream()
                .collect(Collectors.toMap(HighlightRequest::answerId, HighlightRequest::getLineIndexes));

        Map<Long, HighlightLines> highlightLinesByAnswerId = new HashMap<>();
        for (HighlightRequest highlightRequest : highlightsRequest.highlights()) {
            long answerId = highlightRequest.answerId();
            highlightLinesByAnswerId.put(answerId,
                    mapHighlightLine(highlightRequest, textAnswers.get(answerId), answerLineIndexes.get(answerId))
            );
        }
        return highlightLinesByAnswerId;
    }

    private HighlightLines mapHighlightLine(HighlightRequest highlightRequest,
                                       TextAnswer answer, List<Integer> answerLineIndex) {
        HighlightLines highlightLines = new HighlightLines(answer.getContent(), answerLineIndex);
        for (HighlightedLineRequest lineRequest : highlightRequest.lines()) {
            for (HighlightIndexRangeRequest rangeRequest : lineRequest.ranges()) {
                highlightLines.addRange(lineRequest.index(), rangeRequest.startIndex(), rangeRequest.endIndex());
            }
        }
        return highlightLines;
    }

    private void deleteBeforeHighlight(long questionId, TextAnswers textAnswers) {
        List<Long> answerIds = textAnswers.getIdsByQuestionId(questionId);
        highlightRepository.deleteAllByIds(answerIds);
    }

    private void saveHighlight(Map<Long, HighlightLines> highlightContents) {
        List<Highlight> highlights = highlightContents.entrySet()
                .stream()
                .flatMap(entry -> createHighlight(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toList());
        highlightRepository.saveAll(highlights);
    }

    private List<Highlight> createHighlight(long answerId, HighlightLines lines) {
        return lines.getLines()
                .stream()
                .flatMap(line -> line.getRanges().stream()
                        .map(range -> new Highlight(answerId, line.getLineIndex(), range)))
                .toList();
    }
}
