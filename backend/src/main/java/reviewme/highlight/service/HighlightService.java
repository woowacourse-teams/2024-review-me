package reviewme.highlight.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.condition.ConditionsReportEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.HighlightedContent;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.validator.HighlightValidator;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final HighlightValidator highlightValidator;
    private final AnswerRepository answerRepository;
    private final ConditionsReportEndpoint conditionsReportEndpoint;

    @Transactional
    public void highlight(HighlightsRequest request, String reviewRequestCode) {
        long reviewGroupId = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode))
                .getId();

        highlightValidator.validate(request, reviewGroupId);

        // Remove old highlights
        highlightRepository.deleteByReviewGroupIdAndQuestionId(reviewGroupId, request.questionId());

        Map<Long, HighlightedContent> highlightedContents = textAnswerRepository.findAllById(request.answerIds())
                .stream()
                .collect(Collectors.toMap(TextAnswer::getId, answer -> new HighlightedContent(answer.getContent())));

        for (HighlightRequest highlightRequest : request.highlights()) {
            HighlightedContent highlightedContent = highlightedContents.get(highlightRequest.answerId());
            addLines(highlightedContent, highlightRequest.lines());
        }

        saveNewHighlight(request, reviewGroupId);
    }

    private void addLines(HighlightedContent highlightedContent, List<HighlightedLineRequest> lines) {
        for (HighlightedLineRequest lineRequest : lines) {
            long lineIndex = lineRequest.index();
            lineRequest.ranges()
                    .forEach(range -> highlightedContent.addHighlightPosition(
                            lineIndex, range.startIndex(), range.endIndex())
                    );
        }
    }
}
