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

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;

    private final HighlightMapper highlightMapper;
    private final AnswerValidator answerValidator;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest, ReviewGroup reviewGroup) {
        List<Long> requestedAnswerIds = highlightsRequest.getUniqueAnswerIds();
        answerValidator.validateQuestionContainsAnswers(highlightsRequest.questionId(), requestedAnswerIds);
        answerValidator.validateReviewGroupContainsAnswers(reviewGroup, requestedAnswerIds);

        List<Highlight> highlights = highlightMapper.mapToHighlights(highlightsRequest);
        highlightRepository.deleteByReviewGroupIdAndQuestionId(reviewGroup.getId(), highlightsRequest.questionId());
        highlightRepository.saveAll(highlights);
    }
}
