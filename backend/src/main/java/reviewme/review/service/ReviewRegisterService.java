package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.domain.abstraction.NewReviewRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.abstraction.mapper.NewReviewMapper;
import reviewme.review.service.abstraction.validator.NewReviewValidator;
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

    // 리뷰 추상화, 같은 Transactional에 넣어 처리
    private final NewReviewMapper newReviewMapper;
    private final NewReviewValidator newReviewValidator;
    private final NewReviewRepository newReviewRepository;

    @Transactional
    public long registerReview(ReviewRegisterRequest request) {
        Review review = reviewMapper.mapToReview(request);
        reviewValidator.validate(review);
        Review registeredReview = reviewRepository.save(review);

        // 새로운 테이블에 중복해서 저장
        NewReview newReview = newReviewMapper.mapToReview(request);
        newReviewValidator.validate(newReview);
        newReviewRepository.save(newReview);

        return registeredReview.getId();
    }
}
