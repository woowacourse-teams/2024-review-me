package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReceivedReviewsSummaryResponse getReviewSummary(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
        int totalReviewCount = reviewRepository.countByReviewGroupId(reviewGroup.getId());

        return new ReceivedReviewsSummaryResponse(
                reviewGroup.getProjectName(),
                reviewGroup.getReviewee(),
                totalReviewCount
        );
    }
}
