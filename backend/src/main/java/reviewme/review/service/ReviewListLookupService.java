package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.AuthoredReviewElementResponse;
import reviewme.review.service.dto.response.list.AuthoredReviewsResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewPageElementResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewPageResponse;
import reviewme.review.service.mapper.AuthoredReviewMapper;
import reviewme.review.service.mapper.ReceivedReviewMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.util.PageSize;

@Service
@RequiredArgsConstructor
public class ReviewListLookupService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final ReceivedReviewMapper receivedReviewMapper;
    private final AuthoredReviewMapper authoredReviewMapper;

    @Transactional(readOnly = true)
    public ReceivedReviewPageResponse getReceivedReviews(long reviewGroupId,
                                                         @Nullable Long lastReviewId,
                                                         @Nullable Integer size) {
        ReviewGroup reviewGroup = reviewGroupRepository.findById(reviewGroupId)
                .orElseThrow(() -> new ReviewGroupNotFoundException(reviewGroupId));

        PageSize pageSize = new PageSize(size);
        List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(
                reviewGroup.getId(), lastReviewId, pageSize.getSize() + 1);

        boolean isLastPage = reviews.size() <= pageSize.getSize();
        if (!isLastPage) {
            reviews = reviews.subList(0, pageSize.getSize());
        }

        List<ReceivedReviewPageElementResponse> elements = receivedReviewMapper.mapToReviewList(reviews);
        long newLastReviewId = (!elements.isEmpty()) ? elements.get(elements.size() - 1).reviewId() : 0L;

        return new ReceivedReviewPageResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), newLastReviewId, isLastPage, elements);
    }

    public AuthoredReviewsResponse getAuthoredReviews(long loginMemberId,
                                                      @Nullable Long lastReviewId,
                                                      @Nullable Integer size) {
        PageSize pageSize = new PageSize(size);
        List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(
                loginMemberId, lastReviewId, pageSize.getSize() + 1);

        boolean isLastPage = reviews.size() <= pageSize.getSize();
        if (!isLastPage) {
            reviews = reviews.subList(0, pageSize.getSize());
        }

        List<AuthoredReviewElementResponse> elements = authoredReviewMapper.mapToReviewList(reviews);
        long newLastReviewId = (!elements.isEmpty()) ? elements.get(elements.size() - 1).reviewId() : 0L;

        return new AuthoredReviewsResponse(newLastReviewId, isLastPage, elements);
    }
}
