package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReceivedReviewsSummaryResponse getReviewSummary(ReviewGroup reviewGroup) {
        int totalReviewCount = reviewRepository.countByReviewGroupId(reviewGroup.getId());

        return new ReceivedReviewsSummaryResponse(
                reviewGroup.getProjectName(),
                reviewGroup.getReviewee(),
                totalReviewCount
        );
    }
}
