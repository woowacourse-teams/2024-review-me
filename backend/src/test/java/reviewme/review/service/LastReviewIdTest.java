package reviewme.review.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LastReviewIdTest {

    @Test
    void null이_들어오면_기본값이_설정된다() {
        // given, when
        LastReviewId lastReviewId = new LastReviewId(null);

        // then
        assertEquals(Long.MAX_VALUE, lastReviewId.getId());
    }

    @Test
    void 유효한_값이_들어오면_그_값이_설정된다() {
        // given
        long id = 10L;

        // when
        LastReviewId lastReviewId = new LastReviewId(id);

        // then
        assertEquals(id, lastReviewId.getId());
    }
}
