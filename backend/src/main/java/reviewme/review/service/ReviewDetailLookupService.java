package reviewme.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.exception.ReviewNotFoundException;
import reviewme.review.service.mapper.ReviewDetailMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailLookupService {

    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewDetailMapper reviewDetailMapper;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        ReviewGroup reviewGroup = reviewGroupRepository.findById(review.getReviewGroupId())
                .orElseThrow(() -> new ReviewGroupNotFoundException(review.getReviewGroupId()));

        return reviewDetailMapper.mapToReviewDetailResponse(review, reviewGroup);
    }
}
