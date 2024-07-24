package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.MemberFixture.회원_산초;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.keyword.domain.Keyword;
import reviewme.member.domain.Member;
import reviewme.review.domain.exception.IllegalReviewerException;

class ReviewTest {

    @Test
    void 리뷰어와_리뷰이가_다른_경우_예외를_발생한다() {
        // given
        Member member = 회원_산초.create();
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of(꼼꼼하게_기록해요.create(), 의견을_잘_조율해요.create());

        // when, then
        assertThatThrownBy(() -> new Review(member, member, keywords, createdAt))
                .isInstanceOf(IllegalReviewerException.class);
    }
}
