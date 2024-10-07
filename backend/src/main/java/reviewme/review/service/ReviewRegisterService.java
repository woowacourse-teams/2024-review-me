package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.mapper.ReviewMapper;
import reviewme.review.service.validator.ReviewValidator;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private static final Logger log = LoggerFactory.getLogger(ReviewRegisterService.class);

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
