package reviewme.review;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.Keyword;
import reviewme.keyword.KeywordRepository;
import reviewme.member.Member;
import reviewme.member.MemberRepository;
import reviewme.member.MemberResponse;
import reviewme.member.ReviewerGroup;
import reviewme.member.ReviewerGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewerGroupRepository reviewerGroupRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final KeywordRepository keywordRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        Member reviewer = memberRepository.getMemberById(request.reviewerId());
        ReviewerGroup reviewerGroup = reviewerGroupRepository.getReviewerGroupById(request.reviewerGroupId());
        Review review = reviewRepository.save(new Review(reviewer, reviewerGroup));

        List<ReviewContent> contents = request.contents()
                .stream()
                .map(content -> new ReviewContent(review, content.question(), content.answer()))
                .toList();
        reviewContentRepository.saveAll(contents);

        List<ReviewKeyword> reviewKeywords = request.selectedKeywordIds()
                .stream()
                .map(keywordRepository::getKeywordById)
                .map(keyword -> new ReviewKeyword(review, keyword))
                .toList();
        reviewKeywordRepository.saveAll(reviewKeywords);

        return review.getId();
    }

    public ReviewResponse findReview(long id) {
        Review review = reviewRepository.getReviewById(id);

        Member member = memberRepository.getMemberById(review.getReviewer().getId());
        MemberResponse memberResponse = new MemberResponse(member.getId(), member.getName());

        ReviewerGroup reviewerGroup = reviewerGroupRepository.getReviewerGroupById(review.getReviewerGroup().getId());
        ReviewerGroupResponse reviewerGroupResponse = new ReviewerGroupResponse(
                reviewerGroup.getId(),
                reviewerGroup.getGroupName()
        );

        List<ReviewContent> reviewContents = reviewContentRepository.findByReview(review);
        List<ContentResponse> contentResponses = reviewContents.stream()
                .map(reviewContent -> new ContentResponse(
                                reviewContent.getId(),
                                reviewContent.getQuestion(),
                                reviewContent.getAnswer()
                        )
                )
                .toList();

        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findByReview(review);
        List<Keyword> keywords = reviewKeywords.stream()
                .map(ReviewKeyword::getKeyword)
                .toList();
        List<KeywordResponse> keywordResponses = keywords.stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getDetail()))
                .toList();

        return new ReviewResponse(
                review.getId(),
                memberResponse,
                reviewerGroupResponse,
                contentResponses,
                keywordResponses
        );
    }
}
