package reviewme.review.service;

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
import reviewme.member.service.ReviewerGroupService;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.dto.response.ReviewCreationResponse;
import reviewme.review.dto.response.QuestionResponse;
import reviewme.member.dto.response.ReviewCreationReviewerGroupResponse;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewDetailReviewContentResponse;
import reviewme.review.dto.response.ReviewDetailReviewerGroupResponse;
import reviewme.review.exception.ReviewUnAuthorizedException;
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
    private final ReviewContentRepository reviewContentRepository;
    private final KeywordRepository keywordRepository;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        return null;
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
        ReviewCreationReviewerGroupResponse reviewerGroup = reviewerGroupService.findReviewCreationReviewerGroup(reviewerGroupId);
        List<QuestionResponse> questions = questionService.findAllQuestions();
        List<KeywordResponse> keywords = keywordService.findAllKeywords();
        return new ReviewCreationResponse(reviewerGroup, questions, keywords);
    }
}
