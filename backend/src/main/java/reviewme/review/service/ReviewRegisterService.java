package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.module.ReviewMapper;
import reviewme.review.service.module.ReviewValidator;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private final ReviewMapper reviewMapper;
    private final ReviewValidator reviewValidator;

    private final ReviewRepository reviewRepository;

    @Transactional
    public long registerReview(ReviewRegisterRequest request) {
        Review review = reviewMapper.mapToReview(request);
        reviewValidator.validate(review);
        Review registeredReview = reviewRepository.save(review);
        return registeredReview.getId();
    }
}
