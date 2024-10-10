package reviewme.highlight.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.HighLight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.validator.HighlightValidator;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final HighlightValidator highlightValidator;

    @Transactional
    public void highlight(HighlightsRequest request, String reviewRequestCode) {
        long reviewGroupId = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode))
                .getId();

        highlightValidator.validate(request, reviewGroupId);
        deleteOldHighlight(request.questionId(), reviewGroupId);
        saveNewHighlight(request, reviewGroupId);
    }

    private void deleteOldHighlight(long questionId, long reviewGroupId) {
        highlightRepository.deleteByReviewGroupIdAndQuestionId(reviewGroupId, questionId);
    }

    private void saveNewHighlight(HighlightsRequest highlightsRequest) {
        List<Highlight> highlights = new ArrayList<>();
        for (HighlightRequest highlight : highlightsRequest.highlights()) {
            for (HighlightedLineRequest line : highlight.lines()) {
                for (HighlightIndexRangeRequest range : line.ranges()) {
                    Highlight highLight = new Highlight(highlight.answerId(),
                            line.index(), range.startIndex(), range.endIndex()
                    );
                    highlights.add(highLight);
                }
            }
        }
        highlightRepository.saveAll(highlights);
    }
}
