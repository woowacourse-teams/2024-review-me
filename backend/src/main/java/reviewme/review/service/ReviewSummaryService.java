package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewRepository reviewRepository;

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
