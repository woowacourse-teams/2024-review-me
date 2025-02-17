package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReceivedReviewsSummaryResponse getReviewSummary(long reviewGroupId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findById(reviewGroupId)
                .orElseThrow(() -> new ReviewGroupNotFoundException(reviewGroupId));
        int totalReviewCount = reviewRepository.countByReviewGroupId(reviewGroupId);

        return new ReceivedReviewsSummaryResponse(
                reviewGroup.getProjectName(),
                reviewGroup.getReviewee(),
                totalReviewCount
        );
    }
}
