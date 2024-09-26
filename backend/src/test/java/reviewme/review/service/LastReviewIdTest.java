package reviewme.review.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LastReviewIdTest {

    @Test
    void null이_들어오면_기본값이_설정된다() {
        LastReviewId lastReviewId = new LastReviewId(null);
        assertEquals(Long.MAX_VALUE, lastReviewId.getId());
    }

    @Test
    void 유효한_값이_들어오면_그_값이_설정된다() {
        long id = 10L;
        LastReviewId lastReviewId = new LastReviewId(id);
        assertEquals(id, lastReviewId.getId());
    }
}
