package reviewme.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.KeywordRepository;
import reviewme.member.Member;
import reviewme.member.MemberRepository;
import reviewme.member.ReviewerGroup;
import reviewme.member.ReviewerGroupRepository;

import java.util.List;

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
}
