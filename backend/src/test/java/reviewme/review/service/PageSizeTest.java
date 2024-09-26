package reviewme.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PageSizeTest {

    @Test
    void 유효한_값이_들어오면_그_값을_설정한다() {
        int size = 10;

        PageSize pageSize = new PageSize(size);

        assertEquals(size, pageSize.getSize());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 51})
    void 유효한_범위_외의_값이_들어오면_기본값으로_설정한다(Integer size) {
        // given
        int defaultSize = 5;

        // when
        PageSize pageSize = new PageSize(size);

        // then
        assertEquals(defaultSize, pageSize.getSize());
    }

    @Test
    void null이_들어오면_기본값으로_설정한다() {
        // given
        int defaultSize = 5;

        // when
        PageSize pageSize = new PageSize(null);

        // then
        assertEquals(defaultSize, pageSize.getSize());
    }
}
