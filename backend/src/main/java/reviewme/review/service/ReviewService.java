package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.Review2;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.domain.exception.InvalidReviewAccessByReviewGroupException;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.KeywordResponse;
import reviewme.review.dto.response.QuestionSetupResponse;
import reviewme.review.dto.response.ReceivedReviewCategoryResponse;
import reviewme.review.dto.response.ReceivedReviewKeywordsResponse;
import reviewme.review.dto.response.ReceivedReviewResponse;
import reviewme.review.dto.response.ReceivedReviewResponse2;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse2;
import reviewme.review.dto.response.ReviewContentResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewSetupResponse;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.Review2Repository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;
    private final OptionItemRepository optionItemRepository;
    private final Review2Repository review2Repository;

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
                .orElseThrow(() -> new ReviewGroupNotFoundByRequestReviewCodeException(request.reviewRequestCode()));

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
                .orElseThrow(() -> new ReviewGroupNotFoundByRequestReviewCodeException(reviewRequestCode));
        return createReviewSetupResponse(reviewGroup);
    }

    private ReviewSetupResponse createReviewSetupResponse(ReviewGroup reviewGroup) {
        List<QuestionSetupResponse> questionSetupResponse = questionRepository.findAll()
                .stream()
                .map(question -> new QuestionSetupResponse(
                        question.getId(),
                        question.convertContent("{revieweeName}", reviewGroup.getReviewee())
                ))
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
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));

        Review review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(() -> new InvalidReviewAccessByReviewGroupException(reviewId, reviewGroup.getId()));

        return createReviewDetailResponse(review, reviewGroup);
    }

    private ReviewDetailResponse createReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        List<ReviewContentResponse> reviewContents = review.getReviewContents()
                .stream()
                .map(reviewContent -> {
                    Question question = questionRepository.getQuestionById(reviewContent.getQuestionId());
                    return new ReviewContentResponse(
                            reviewContent.getId(),
                            question.convertContent("{revieweeName}", reviewGroup.getReviewee()),
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
                review.getCreatedDate(),
                reviewGroup.getProjectName(),
                reviewGroup.getReviewee(),
                reviewContents,
                keywords
        );
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse2 findReceivedReviews2(String groupAccessCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));

        List<ReceivedReviewResponse2> reviewResponses =
                review2Repository.findReceivedReviewsByGroupId(reviewGroup.getId())
                        .stream()
                        .map(this::createReceivedReviewResponse2)
                        .toList();

        return new ReceivedReviewsResponse2(reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewResponses);
    }

    private ReceivedReviewResponse2 createReceivedReviewResponse2(Review2 review) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findByOptionTypeAndReviewId(review.getId(),
                OptionType.CATEGORY);

        List<ReceivedReviewCategoryResponse> categoryResponses = categoryOptionItems.stream()
                .map(optionItem -> new ReceivedReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();


        return new ReceivedReviewResponse2(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewPreviewGenerator.generatePreview2(review.getTextAnswers()),
                categoryResponses
        );
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse findReceivedReviews(String groupAccessCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));
        List<ReceivedReviewResponse> reviewResponses =
                reviewRepository.findReceivedReviewsByGroupId(reviewGroup.getId())
                        .stream()
                        .map(this::createReceivedReviewResponse)
                        .toList();
        return new ReceivedReviewsResponse(reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewResponses);
    }

    private ReceivedReviewResponse createReceivedReviewResponse(Review review) {
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
