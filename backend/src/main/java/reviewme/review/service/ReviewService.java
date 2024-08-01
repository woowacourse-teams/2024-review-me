package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.domain.exception.ReviewGroupNotFoundException;
import reviewme.review.domain.exception.ReviewIsNotInReviewGroupException;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.KeywordResponse;
import reviewme.review.dto.response.QuestionSetupResponse;
import reviewme.review.dto.response.ReceivedReviewKeywordsResponse;
import reviewme.review.dto.response.ReceivedReviewResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReviewContentResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewSetupResponse;
import reviewme.review.repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;

    private final ReviewCreationQuestionValidator reviewCreationQuestionValidator;
    private final ReviewCreationKeywordValidator reviewCreationKeywordValidator;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        Review savedReview = saveReview(request);
        saveReviewKeywords(request.keywords(), savedReview.getId());
        return savedReview.getId();
    }

    private Review saveReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(request.reviewRequestCode())
                .orElseThrow(ReviewGroupNotFoundException::new);

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

        return reviewRepository.save(review);
    }

    private void saveReviewKeywords(List<Long> selectedKeywordIds, long savedReviewId) {
        reviewCreationKeywordValidator.validate(selectedKeywordIds);
        List<ReviewKeyword> reviewKeywords = selectedKeywordIds.stream()
                .map(keyword -> new ReviewKeyword(savedReviewId, keyword))
                .toList();
        reviewKeywordRepository.saveAll(reviewKeywords);
    }

    @Transactional(readOnly = true)
    public ReviewSetupResponse findReviewCreationSetup(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(ReviewGroupNotFoundException::new);
        return createReviewSetupResponse(reviewGroup);
    }

    private ReviewSetupResponse createReviewSetupResponse(ReviewGroup reviewGroup) {
        List<QuestionSetupResponse> questionSetupResponse = questionRepository.findAll()
                .stream()
                .map(question -> new QuestionSetupResponse(question.getId(), question.getContent()))
                .toList();

        List<KeywordResponse> keywordResponse = keywordRepository.findAll()
                .stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getContent()))
                .toList();

        return new ReviewSetupResponse(
                reviewGroup.getReviewee(), reviewGroup.getProjectName(), questionSetupResponse, keywordResponse
        );
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findReceivedReviewDetail(String groupAccessCode, long reviewId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(ReviewGroupNotFoundException::new);

        Review review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(ReviewIsNotInReviewGroupException::new);

        return createReviewDetailResponse(review, reviewGroup);
    }

    private ReviewDetailResponse createReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        List<ReviewContentResponse> reviewContents = review.getReviewContents()
                .stream()
                .map(reviewContent -> {
                    Question question = questionRepository.getQuestionById(reviewContent.getQuestionId());
                    return new ReviewContentResponse(reviewContent.getId(), question.getContent(),
                            reviewContent.getAnswer());
                })
                .toList();

        List<KeywordResponse> keywords = reviewKeywordRepository.findAllByReviewId(review.getId())
                .stream()
                .map(reviewKeyword -> keywordRepository.getKeywordById(reviewKeyword.getKeywordId()))
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getContent()))
                .toList();

        return new ReviewDetailResponse(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewGroup.getProjectName(),
                reviewGroup.getReviewee(),
                reviewContents,
                keywords
        );
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse findReceivedReviews(String groupAccessCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(ReviewGroupNotFoundException::new);
        List<ReceivedReviewResponse> reviewResponses = reviewRepository.findAllByReviewGroupId(reviewGroup.getId())
                .stream()
                .map(this::extractResponse)
                .toList();
        return new ReceivedReviewsResponse(reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewResponses);
    }

    private ReceivedReviewResponse extractResponse(Review review) {
        List<ReceivedReviewKeywordsResponse> keywordsResponses =
                reviewKeywordRepository.findAllByReviewId(review.getId())
                        .stream()
                        .map(reviewKeyword -> keywordRepository.getKeywordById(reviewKeyword.getKeywordId()))
                        .map(keyword -> new ReceivedReviewKeywordsResponse(keyword.getId(), keyword.getContent()))
                        .toList();
        return new ReceivedReviewResponse(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewPreviewGenerator.generatePreview(review.getReviewContents()),
                keywordsResponses
        );
    }
}
