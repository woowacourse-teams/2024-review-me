package reviewme.highlight.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.mapper.HighlightMapper;
import reviewme.review.service.validator.AnswerValidator;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final HighlightMapper highlightMapper;
    private final AnswerValidator answerValidator;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest) {
        ReviewGroup reviewGroup = reviewGroupRepository.findById(highlightsRequest.reviewGroupId())
                .orElseThrow(() -> new ReviewGroupNotFoundException(highlightsRequest.reviewGroupId()));
        List<Long> requestedAnswerIds = highlightsRequest.getUniqueAnswerIds();
        answerValidator.validateQuestionContainsAnswers(highlightsRequest.questionId(), requestedAnswerIds);
        answerValidator.validateReviewGroupContainsAnswers(reviewGroup, requestedAnswerIds);

        List<Highlight> highlights = highlightMapper.mapToHighlights(highlightsRequest);
        highlightRepository.deleteByReviewGroupIdAndQuestionId(reviewGroup.getId(), highlightsRequest.questionId());
        highlightRepository.saveAll(highlights);
    }
}
