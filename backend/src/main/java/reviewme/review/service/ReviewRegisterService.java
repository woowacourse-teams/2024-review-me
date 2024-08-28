package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.module.CheckBoxAnswerValidator;
import reviewme.review.service.module.ReviewMapper;
import reviewme.review.service.module.ReviewValidator;
import reviewme.review.service.module.TextAnswerValidator;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private final ReviewMapper reviewMapper;
    private final ReviewValidator reviewValidator;
    private final TextAnswerValidator textAnswerValidator;
    private final CheckBoxAnswerValidator checkBoxAnswerValidator;

    private final ReviewRepository reviewRepository;

    @Transactional
    public long registerReview(CreateReviewRequest request) {
        Review review = reviewMapper.mapToReview(request, textAnswerValidator, checkBoxAnswerValidator);
        reviewValidator.validate(review);
        Review registeredReview = reviewRepository.save(review);
        return registeredReview.getId();
    }
}
