package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.dto.response.MemberResponse;
import reviewme.member.dto.response.ReviewerGroupResponse;
import reviewme.member.repository.GithubReviewerGroupRepository;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewContentResponse;
import reviewme.review.dto.response.ReviewResponse;
import reviewme.review.exception.GithubReviewerGroupNotFoundException;
import reviewme.review.exception.ReviewContentExistException;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewerGroupRepository reviewerGroupRepository;
    private final GithubReviewerGroupRepository githubReviewerGroupRepository;
    private final ReviewContentRepository reviewContentRepository;
    private final KeywordRepository keywordRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;

    @Transactional
    public Long createReview(CreateReviewRequest request) {
        Member reviewer = memberRepository.getMemberById(request.reviewerId());
        ReviewerGroup reviewerGroup = reviewerGroupRepository.getReviewerGroupById(request.reviewerGroupId());

        boolean isValidReviewer = githubReviewerGroupRepository.existsByGithubIdAndReviewerGroup(
                reviewer.getGithubId(),
                reviewerGroup
        );
        if (!isValidReviewer) {
            throw new GithubReviewerGroupNotFoundException();
        }

        if (reviewRepository.existsByReviewerAndReviewerGroup(reviewer, reviewerGroup)) {
            throw new ReviewContentExistException();
        }

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
        Member reviewee = reviewerGroup.getReviewee();
        ReviewerGroupResponse reviewerGroupResponse = new ReviewerGroupResponse(
                reviewerGroup.getId(),
                reviewerGroup.getGroupName(),
                reviewerGroup.getDeadline(),
                new MemberResponse(reviewee.getId(), reviewee.getName())
        );

        List<ReviewContent> reviewContents = reviewContentRepository.findByReview(review);
        List<ReviewContentResponse> reviewContentRespons = reviewContents.stream()
                .map(reviewContent -> new ReviewContentResponse(
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
                reviewContentRespons,
                keywordResponses
        );
    }
}
