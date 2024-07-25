package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.Keywords;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.keyword.service.KeywordService;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.member.service.ReviewerGroupService;
import reviewme.review.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReceivedReviewKeywordsResponse;
import reviewme.review.dto.response.ReceivedReviewResponse;
import reviewme.review.dto.response.ReceivedReviewReviewerGroupResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewDetailReviewContentResponse;
import reviewme.review.dto.response.ReviewDetailReviewerGroupResponse;
import reviewme.review.exception.ReviewUnAuthorizedException;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewerGroupService reviewerGroupService;
    private final KeywordService keywordService;
    private final QuestionService questionService;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewerGroupRepository reviewerGroupRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        ReviewerGroup reviewerGroup = reviewerGroupRepository.getReviewerGroupById(request.reviewerGroupId());
        Member reviewer = memberRepository.getMemberById(request.reviewerId());

        List<Keyword> keywordList = request.keywords()
                .stream()
                .map(keywordRepository::getKeywordById)
                .toList();

        Review review = new Review(reviewer, reviewerGroup.getReviewee(),
                reviewerGroup, keywordList, LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);

        request.reviewContents()
                .forEach(contentsRequest -> {
                    Question question = questionRepository.getQuestionById(contentsRequest.questionId());
                    String answer = contentsRequest.answer();

                    ReviewContent reviewContent = new ReviewContent(savedReview, question, answer);
                    reviewContentRepository.save(reviewContent);
                });

        return savedReview.getId();
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponse findReview(long reviewId, long memberId) {
        Review review = reviewRepository.getReviewById(reviewId);
        Member member = memberRepository.getMemberById(memberId);
        if (!review.isForReviewee(member)) {
            throw new ReviewUnAuthorizedException();
        }

        ReviewerGroup reviewerGroup = review.getReviewerGroup();
        Keywords keywords = review.getKeywords();
        List<ReviewContent> reviewContents = reviewContentRepository.findAllByReviewId(reviewId);

        ReviewDetailReviewerGroupResponse reviewerGroupResponse = new ReviewDetailReviewerGroupResponse(
                reviewerGroup.getId(),
                reviewerGroup.getGroupName(),
                reviewerGroup.getDescription(),
                reviewerGroup.getThumbnailUrl()
        );
        List<ReviewDetailReviewContentResponse> reviewContentResponses = reviewContents.stream()
                .map(content -> new ReviewDetailReviewContentResponse(
                        content.getQuestion(),
                        content.getAnswer()
                ))
                .toList();
        List<String> keywordContents = keywords.getKeywordIds()
                .stream()
                .map(keywordRepository::getKeywordById)
                .map(Keyword::getContent)
                .toList();

        return new ReviewDetailResponse(
                reviewId,
                review.getCreatedAt().toLocalDate(),
                review.isPublic(),
                reviewerGroupResponse,
                reviewContentResponses,
                keywordContents
        );
    }

    @Transactional(readOnly = true)
    public ReviewCreationResponse findReviewCreationSetup(long reviewerGroupId) {
        ReviewCreationReviewerGroupResponse reviewerGroup = reviewerGroupService.findReviewCreationReviewerGroup(
                reviewerGroupId);
        List<QuestionResponse> questions = questionService.findAllQuestions();
        List<KeywordResponse> keywords = keywordService.findAllKeywords();
        return new ReviewCreationResponse(reviewerGroup, questions, keywords);
    }

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse findMyReceivedReview(long memberId, Long lastReviewId, int size) {
        List<Review> reviews = reviewRepository.findLimitedReviewsWrittenForReviewee(memberId, lastReviewId, size);

        if (reviews.isEmpty()) {
            return new ReceivedReviewsResponse(0, 0, List.of());
        }

        return new ReceivedReviewsResponse(
                reviews.size(),
                reviews.get(reviews.size() - 1).getId(),
                reviews.stream()
                        .map(this::createReceivedReviewResponse)
                        .toList());
    }

    private ReceivedReviewResponse createReceivedReviewResponse(Review review) {
        return new ReceivedReviewResponse(
                review.getId(),
                review.isPublic(),
                review.getCreatedAt().toLocalDate(),
                createReviewContentPreview(review),
                new ReceivedReviewReviewerGroupResponse(
                        review.getReviewerGroup().getId(),
                        review.getReviewerGroup().getGroupName(),
                        review.getReviewerGroup().getThumbnailUrl()
                ),
                createKeywordResponse(review));
    }

    private String createReviewContentPreview(Review review) {
        return reviewContentRepository.findAllByReviewId(review.getId())
                .get(0)
                .getAnswerPreview();
    }

    private List<ReceivedReviewKeywordsResponse> createKeywordResponse(Review review) {
        return review.getKeywords().getKeywordIds()
                .stream()
                .map(keywordRepository::getKeywordById)
                .map(keyword -> new ReceivedReviewKeywordsResponse(
                        keyword.getId(),
                        keyword.getContent()
                ))
                .toList();
    }
}
