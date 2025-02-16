package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.AuthoredReviewsResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewPageElementResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewPageResponse;
import reviewme.review.service.mapper.ReviewListMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.util.PageSize;

@Service
@RequiredArgsConstructor
public class ReviewListLookupService {

    private final ReviewRepository reviewRepository;
    private final ReviewListMapper reviewListMapper;
    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReceivedReviewPageResponse getReceivedReviews(String reviewRequestCode,
                                                         @Nullable Long lastReviewId, @Nullable Integer size) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
        PageSize pageSize = new PageSize(size);
        List<ReceivedReviewPageElementResponse> reviewListResponse
                = reviewListMapper.mapToReviewList(reviewGroup, lastReviewId, pageSize.getSize());
        long newLastReviewId = calculateLastReviewId(reviewListResponse);
        boolean isLastPage = isLastPage(reviewListResponse, reviewGroup);
        return new ReceivedReviewPageResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), newLastReviewId, isLastPage, reviewListResponse
        );
    }

    public AuthoredReviewsResponse getAuthoredReviews(Long lastReviewId, Integer size, long loginMemberId) {
        // TODO: 생성일자 최신순 정렬
        return null;
    }

    private long calculateLastReviewId(List<ReceivedReviewPageElementResponse> elements) {
        if (elements.isEmpty()) {
            return 0;
        }
        return elements.get(elements.size() - 1).reviewId();
    }

    private boolean isLastPage(List<ReceivedReviewPageElementResponse> elements, ReviewGroup reviewGroup) {
        if (elements.isEmpty()) {
            return true;
        }

        ReceivedReviewPageElementResponse lastReviewResponse = elements.get(elements.size() - 1);
        return !reviewRepository.existsOlderReviewInGroup(
                reviewGroup.getId(), lastReviewResponse.reviewId(), lastReviewResponse.createdAt());
    }
}
