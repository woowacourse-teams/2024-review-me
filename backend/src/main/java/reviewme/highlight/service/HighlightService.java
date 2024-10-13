package reviewme.highlight.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightContent;
import reviewme.highlight.domain.HighlightLine;
import reviewme.highlight.domain.HighlightRange;
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
    public void highlight(HighlightsRequest highlightsRequest, String reviewRequestCode) {
        long reviewGroupId = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode))
                .getId();
        List<TextAnswer> textAnswersByIds = textAnswerRepository.findAllById(highlightsRequest.getUniqueAnswerIds());
        TextAnswers textAnswers = new TextAnswers(textAnswersByIds);

        highlightValidator.validate(highlightsRequest, reviewGroupId);
        List<HighlightContent> highlights = mapHighlights(highlightsRequest, textAnswers);
        deleteBeforeHighlight(highlightsRequest.questionId(), textAnswers);
        saveHighlight(highlights);
    }

    private List<HighlightContent> mapHighlights(HighlightsRequest highlightsRequest, TextAnswers textAnswers) {

        Map<Long, List<Integer>> answerLineIndexs = highlightsRequest.highlights()
                .stream()
                .collect(Collectors.toMap(HighlightRequest::answerId, HighlightRequest::getLineIndexs));

        List<HighlightContent> highlightContents = new ArrayList<>();
        for (HighlightRequest highlightRequest : highlightsRequest.highlights()) {
            long answerId = highlightRequest.answerId();
            highlightContents.add(
                    mapContent(highlightRequest, textAnswers.get(answerId), answerLineIndexs.get(answerId))
            );
        }
        return highlightContents;
    }

    private HighlightContent mapContent(HighlightRequest highlightRequest,
                                        TextAnswer answer, List<Integer> answerLineIndex) {
        HighlightContent highlightContent = new HighlightContent(answer, answerLineIndex);
        for (HighlightedLineRequest lineRequest : highlightRequest.lines()) {
            for (HighlightIndexRangeRequest rangeRequest : lineRequest.ranges()) {
                highlightContent.addRange(lineRequest.index(), rangeRequest.startIndex(), rangeRequest.endIndex());
            }
        }
        return highlightContent;
    }

    private void deleteBeforeHighlight(long questionId, TextAnswers textAnswers) {
        List<Long> answerIds = textAnswers.getIdsByQuestionId(questionId);
        highlightRepository.deleteAllByIds(answerIds);
    }

    private void saveHighlight(List<HighlightContent> highlightContents) {
        List<Highlight> highlights = new ArrayList<>();
        highlightContents.forEach(highlightContent -> addHighlight(highlightContent, highlights));
        highlightRepository.saveAll(highlights);
    }

    private void addHighlight(HighlightContent highlightContent, List<Highlight> highlights) {
        for (HighlightLine line : highlightContent.getLines()) {
            for (HighlightRange range : line.getRanges()) {
                Highlight highlight = new Highlight(
                        highlightContent.getAnswerId(), line.getLineIndex(), range.getStartIndex(), range.getEndIndex()
                );
                highlights.add(highlight);
            }
        }
    }
}
