package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final ReviewCreationQuestionValidator reviewCreationQuestionValidator;
    private final ReviewCreationKeywordValidator reviewCreationKeywordValidator;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        Review savedReview = saveReview(request);
        saveReviewKeywords(request.keywords(), savedReview.getId());
        return savedReview.getId();
    }

    private Review saveReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.getReviewGroupByReviewRequestCode(request.reviewRequestCode());
        List<Long> questionIds = request.reviewContents()
                .stream()
                .map(CreateReviewContentRequest::questionId)
                .toList();
        reviewCreationQuestionValidator.validate(questionIds);

        List<ReviewContent> reviewContents = request.reviewContents()
                .stream()
                .map(r -> new ReviewContent(r.questionId(), r.answer()))
                .toList();
        Review review = new Review(reviewGroup.getId(), reviewContents, LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        reviewContentRepository.saveAll(reviewContents);
        return savedReview;
    }

    private void saveReviewKeywords(List<Long> selectedKeywordIds, long savedReviewId) {
        reviewCreationKeywordValidator.validate(selectedKeywordIds);
        List<ReviewKeyword> reviewKeywords = selectedKeywordIds.stream()
                .map(keyword -> new ReviewKeyword(savedReviewId, keyword))
                .toList();
        reviewKeywordRepository.saveAll(reviewKeywords);
    }
}
