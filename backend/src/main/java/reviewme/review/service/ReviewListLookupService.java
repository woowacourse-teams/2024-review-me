package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse3;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.review.service.mapper.ReviewListMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewListLookupService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewListMapper reviewListMapper;

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse getReceivedReviews(String reviewRequestCode, String groupAccessCode) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(reviewRequestCode);
        validateGroupAccessCode(groupAccessCode, reviewGroup);

        List<ReviewListElementResponse> reviewGroupResponse = reviewListMapper.mapToReviewList(reviewGroup);
        return new ReceivedReviewsResponse(reviewGroup.getReviewee(), reviewGroup.getProjectName(),
                reviewGroupResponse);
    }

    private ReviewGroup findReviewGroupByRequestCodeOrThrow(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    private static void validateGroupAccessCode(String groupAccessCode, ReviewGroup reviewGroup) {
        if (!reviewGroup.matchesGroupAccessCode(groupAccessCode)) {
            throw new ReviewGroupUnauthorizedException(reviewGroup.getId());
        }
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse getReceivedReviews2(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        List<ReviewListElementResponse> reviewGroupResponse = reviewListMapper.mapToReviewList(reviewGroup);
        return new ReceivedReviewsResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewGroupResponse
        );
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse3 getReceivedReviews3(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        List<ReviewListElementResponse> reviewGroupResponse = reviewListMapper.mapToReviewList(reviewGroup);
        return null;
    }
}
