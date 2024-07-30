package reviewme.review.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

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
    private final ReviewGroupRepository reviewGroupRepository;

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

        Review savedReview = saveReview(request);
        return savedReview.getId();
    }

    private Review saveReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.getReviewGroupByReviewRequestCode(request.reviewRequestCode());
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
