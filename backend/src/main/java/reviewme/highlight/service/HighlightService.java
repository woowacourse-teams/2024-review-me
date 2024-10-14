package reviewme.highlight.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.mapper.HighlightMapper;
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
    private final HighlightMapper highlightMapper;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest, String reviewRequestCode) {
        long reviewGroupId = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode))
                .getId();
        List<TextAnswer> textAnswersByIds = textAnswerRepository.findAllById(highlightsRequest.getUniqueAnswerIds());

        highlightValidator.validate(highlightsRequest, reviewGroupId);
        List<Highlight> highlights = highlightMapper.mapToHighlights(highlightsRequest, textAnswersByIds);

        TextAnswers textAnswers = new TextAnswers(textAnswersByIds);
        List<Long> answerIds = textAnswers.getIdsByQuestionId(highlightsRequest.questionId());
        highlightRepository.deleteAllByIds(answerIds);

        highlightRepository.saveAll(highlights);
    }
}
