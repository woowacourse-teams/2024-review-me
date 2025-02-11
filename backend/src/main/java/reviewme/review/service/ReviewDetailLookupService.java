package reviewme.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.exception.ReviewNotFoundByIdException;
import reviewme.review.service.mapper.ReviewDetailMapper;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailLookupService {

    private final ReviewRepository reviewRepository;
    private final ReviewDetailMapper reviewDetailMapper;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewDetail(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundByIdException(reviewId));

        return reviewDetailMapper.mapToReviewDetailResponse(review);
    }
}
