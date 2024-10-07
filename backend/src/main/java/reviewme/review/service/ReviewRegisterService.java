package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.NewReview;
import reviewme.review.repository.NewReviewRepository;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.mapper.NewReviewMapper;
import reviewme.review.service.validator.NewReviewValidator;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private static final Logger log = LoggerFactory.getLogger(ReviewRegisterService.class);
    private final NewReviewMapper reviewMapper;
    private final NewReviewValidator reviewValidator;

    private final NewReviewRepository reviewRepository;

    // 리뷰 추상화, 같은 Transactional에 넣어 처리
    private final NewReviewMapper newReviewMapper;
    private final NewReviewValidator newReviewValidator;
    private final NewReviewRepository newReviewRepository;

    @Transactional
    public long registerReview(ReviewRegisterRequest request) {
        NewReview newReview = newReviewMapper.mapToReview(request);
        newReviewValidator.validate(newReview);
        NewReview registeredReview = newReviewRepository.save(newReview);

        return registeredReview.getId();
    }
}
