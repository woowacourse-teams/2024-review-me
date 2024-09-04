package reviewme.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.review.service.exception.ReviewNotFoundByIdAndGroupException;
import reviewme.review.service.module.ReviewDetailMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailLookupService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final ReviewDetailMapper reviewDetailMapper;

    public ReviewDetailResponse getReviewDetail(long reviewId, String reviewRequestCode, String groupAccessCode) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(reviewRequestCode);
        validateGroupAccessCode(groupAccessCode, reviewGroup);

        Review review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(() -> new ReviewNotFoundByIdAndGroupException(reviewId, reviewGroup.getId()));

        return reviewDetailMapper.mapToReviewDetailResponse(review, reviewGroup);
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
}
