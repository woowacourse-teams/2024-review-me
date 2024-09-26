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
    private final ReviewListMapper reviewListMapper;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse getReceivedReviews(String reviewRequestCode,
                                                      Long lastReviewId, Integer size) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        LastReviewId lastId = new LastReviewId(lastReviewId);
        PageSize pageSize = new PageSize(size);
        List<ReviewListElementResponse> reviewListElements
                = reviewListMapper.mapToReviewList(reviewGroup, lastId.getId(), pageSize.getSize());
        int totalSize = reviewRepository.countByReviewGroupId(reviewGroup.getId());
        long newLastReviewId = calculateLastReviewId(reviewListElements);
        return new ReceivedReviewsResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), totalSize, newLastReviewId, reviewListElements
        );
    }

    private long calculateLastReviewId(List<ReviewListElementResponse> elements) {
        if (elements.isEmpty()) {
            return 0;
        }
        return elements.get(elements.size() - 1).reviewId();
    }
}
