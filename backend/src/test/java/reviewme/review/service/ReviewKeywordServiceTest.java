package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.ReviewerGroupFixture.리뷰_그룹;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.KeywordFixture;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewKeywordServiceTest {

    @Autowired
    private ReviewKeywordService reviewKeywordService;

    @Autowired
    private ReviewKeywordRepository reviewKeywordRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewerGroupRepository reviewerGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰에_키워드를_추가한다() {
        // given
        Member sancho = memberRepository.save(new Member("산초"));
        Member kirby = memberRepository.save(new Member("커비"));
        ReviewerGroup group = reviewerGroupRepository.save(리뷰_그룹.create(sancho));
        Review review = reviewRepository.save(new Review(kirby, group));

        List<Keyword> keywords = Stream.of(꼼꼼하게_기록해요, 회의를_이끌어요, 의견을_잘_조율해요)
                .map(KeywordFixture::create)
                .map(keywordRepository::save)
                .toList();
        List<Keyword> selectedKeywords = List.of(keywords.get(0), keywords.get(1));

        // when
        reviewKeywordService.attachSelectedKeywordsOnReview(review, selectedKeywords);

        // then
        List<ReviewKeyword> actual = reviewKeywordRepository.findByReview(review);
        assertThat(actual).hasSize(2);
    }
}
