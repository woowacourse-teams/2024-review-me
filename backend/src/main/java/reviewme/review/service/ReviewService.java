package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;
import reviewme.question.domain.exception.DuplicateQuestionException;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.exception.QuestionNotFoundException;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final int MAX_KEYWORD_COUNT = 5;
    private static final int MIN_KEYWORD_COUNT = 1;

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        Review savedReview = saveReview(request);
        saveReviewKeywords(request.keywords(), savedReview.getId());
        return savedReview.getId();
    }

    private Review saveReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.getReviewGroupByReviewRequestCode(request.reviewRequestCode());
        validateQuestion(request.reviewContents());
        List<ReviewContent> reviewContents = request.reviewContents()
                .stream()
                .map(r -> new ReviewContent(r.questionId(), r.answer()))
                .toList();
        Review review = new Review(reviewGroup.getId(), reviewContents, LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        reviewContentRepository.saveAll(reviewContents);
        return savedReview;
    }

    private void validateQuestion(List<CreateReviewContentRequest> createReviewContentRequests) {
        int questionsCount = createReviewContentRequests.size();
        long distinctCount = createReviewContentRequests.stream()
                .map(CreateReviewContentRequest::questionId)
                .distinct()
                .count();
        if (questionsCount != distinctCount) {
            throw new DuplicateQuestionException();
        }

        boolean doesExistsQuestion = createReviewContentRequests.stream()
                .anyMatch(content -> questionRepository.existsById(content.questionId()));
        if (!doesExistsQuestion) {
            throw new QuestionNotFoundException();
        }
    }

    private void saveReviewKeywords(List<Long> selectedKeywordIds, long savedReviewId) {
        validateKeywords(selectedKeywordIds);
        List<ReviewKeyword> reviewKeywords = selectedKeywordIds.stream()
                .map(keyword -> new ReviewKeyword(savedReviewId, keyword))
                .toList();
        reviewKeywordRepository.saveAll(reviewKeywords);
    }

    private void validateKeywords(List<Long> selectedKeywordIds) {
        int keywordsSize = selectedKeywordIds.size();
        long distinctCount = selectedKeywordIds.stream()
                .distinct()
                .count();
        if (distinctCount != keywordsSize) {
            throw new DuplicateKeywordException();
        }

        if (keywordsSize < MIN_KEYWORD_COUNT || keywordsSize > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MIN_KEYWORD_COUNT, MAX_KEYWORD_COUNT);
        }
    }
}
