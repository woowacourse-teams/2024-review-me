package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.ReviewerGroupFixture.리뷰_그룹;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.fixture.KeywordFixture;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewKeyword;

@DataJpaTest
class ReviewKeywordRepositoryTest {

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
    void 리뷰에_해당하는_키워드를_모두_삭제한다() {
        // given
        Member sancho = memberRepository.save(new Member("산초", "sancho"));
        Member kirby = memberRepository.save(new Member("커비", "kirby"));
        ReviewerGroup group = reviewerGroupRepository.save(리뷰_그룹.create(sancho));
        Review review = reviewRepository.save(new Review(kirby, group));
        List<ReviewKeyword> reviewKeywords = Stream.of(꼼꼼하게_기록해요, 회의를_이끌어요, 의견을_잘_조율해요)
                .map(KeywordFixture::create)
                .map(keywordRepository::save)
                .map(keyword -> new ReviewKeyword(review, keyword))
                .toList();
        reviewKeywordRepository.saveAll(reviewKeywords);

        // when
        reviewKeywordRepository.deleteAllByReview(review);

        // then
        List<ReviewKeyword> actual = reviewKeywordRepository.findByReview(review);
        assertThat(actual).isEmpty();
    }
}
