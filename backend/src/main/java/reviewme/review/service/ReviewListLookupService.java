package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.mapper.ReviewListMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewListLookupService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewListMapper reviewListMapper;

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse getReceivedReviews(Long lastReviewId, Integer size, String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        PageSize pageSize = new PageSize(size);
        List<ReviewListElementResponse> reviewListResponse
                = reviewListMapper.mapToReviewList(reviewGroup, lastReviewId, pageSize.getSize());
        long newLastReviewId = calculateLastReviewId(reviewListResponse);
        boolean isLastPage = isLastPage(reviewListResponse, reviewGroup);
        return new ReceivedReviewsResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), newLastReviewId, isLastPage, reviewListResponse
        );
    }

    private long calculateLastReviewId(List<ReviewListElementResponse> elements) {
        if (elements.isEmpty()) {
            return 0;
        }
        return elements.get(elements.size() - 1).reviewId();
    }

    private boolean isLastPage(List<ReviewListElementResponse> elements, ReviewGroup reviewGroup) {
        if (elements.isEmpty()) {
            return true;
        }

        return reviewRepository.isSmallestReviewIdByReviewGroupId(reviewGroup.getId(), calculateLastReviewId(elements));
    }
}
