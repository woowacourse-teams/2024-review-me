package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReviewCountResponse;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReviewCountResponse getReviewCountByGroup(long reviewGroupId) {
        int totalReviewCount = reviewRepository.countByReviewGroupId(reviewGroupId);

        return new ReviewCountResponse(
                reviewGroupId,
                totalReviewCount
        );
    }
}
