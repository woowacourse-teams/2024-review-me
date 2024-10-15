package reviewme.highlight.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.mapper.HighlightMapper;
import reviewme.highlight.service.validator.HighlightValidator;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final AnswerRepository answerRepository;

    private final HighlightValidator highlightValidator;
    private final HighlightMapper highlightMapper;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest, String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        highlightValidator.validate(highlightsRequest, reviewGroup);
        List<Highlight> highlights = highlightMapper.mapToHighlights(highlightsRequest);

        Set<Long> allAnswerIds = answerRepository.findIdsByQuestionId(highlightsRequest.questionId());
        highlightRepository.deleteAllByAnswerIds(allAnswerIds);

        highlightRepository.saveAll(highlights);
    }
}
