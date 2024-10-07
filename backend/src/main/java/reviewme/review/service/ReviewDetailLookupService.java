package reviewme.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.NewReview;
import reviewme.review.repository.NewReviewRepository;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.ReviewNotFoundByIdAndGroupException;
import reviewme.review.service.mapper.ReviewDetailMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailLookupService {

    private final NewReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    private final ReviewDetailMapper reviewDetailMapper;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(long reviewId, String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        NewReview review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(() -> new ReviewNotFoundByIdAndGroupException(reviewId, reviewGroup.getId()));

        return reviewDetailMapper.mapToReviewDetailResponse(review, reviewGroup);
    }
}
