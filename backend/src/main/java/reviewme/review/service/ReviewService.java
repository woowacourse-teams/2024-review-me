package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.Keywords;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.MemberRepository;
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
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

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

    public ReceivedReviewsResponse findMyReceivedReview(long memberId, long lastReviewId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Review> reviews = reviewRepository.findAllByRevieweeBeforeLastViewedId(memberId, lastReviewId,
                pageRequest);
        int totalSize = reviews.getContent().size();

        if (totalSize == 0) {
            return new ReceivedReviewsResponse(0, 0, List.of());
        }

        return new ReceivedReviewsResponse(
                reviews.getContent().size(),
                reviews.getContent().get(totalSize - 1).getId(),
                reviews.getContent().stream()
                        .map(review -> new ReceivedReviewResponse(
                                review.getId(),
                                review.isPublic(),
                                review.getCreatedAt().toLocalDate(),
                                getReviewContentPreview(review),
                                new ReceivedReviewReviewerGroupResponse(
                                        review.getReviewerGroup().getId(),
                                        review.getReviewerGroup().getGroupName(),
                                        review.getReviewerGroup().getThumbnailUrl()
                                ),
                                getKeywordResponse(review)))
                        .toList());
    }

    private String getReviewContentPreview(Review review) {
        String answer = reviewContentRepository.findAllByReviewId(review.getId())
                .get(0)
                .getAnswer();
        if (answer.length() > 150) {
            return answer.substring(0, 150);
        }
        return answer;
    }

    private List<ReceivedReviewKeywordsResponse> getKeywordResponse(Review review) {
        return review.getKeywords().getKeywordIds().stream()
                .map(keywordRepository::getKeywordById)
                .map(keyword -> new ReceivedReviewKeywordsResponse(
                        keyword.getId(),
                        keyword.getContent()
                ))
                .toList();
    }
}
