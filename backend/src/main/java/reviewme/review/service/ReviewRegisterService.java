package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.domain.abstraction.NewReviewRepository;
import reviewme.review.service.abstraction.mapper.NewReviewMapper;
import reviewme.review.service.abstraction.validator.NewReviewValidator;
import reviewme.review.service.dto.request.ReviewRegisterRequest;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private final NewReviewMapper reviewMapper;
    private final NewReviewValidator reviewValidator;
    private final NewReviewRepository reviewRepository;

    @Transactional
    public long registerReview(ReviewRegisterRequest request) {
        NewReview newReview = reviewMapper.mapToReview(request);
        reviewValidator.validate(newReview);
        NewReview registeredReview = reviewRepository.save(newReview);

        return registeredReview.getId();
    }
}
