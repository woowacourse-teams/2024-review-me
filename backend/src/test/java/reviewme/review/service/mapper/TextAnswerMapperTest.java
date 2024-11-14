package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

class TextAnswerMapperTest {

    @Test
    void 텍스트_답변을_요청으로부터_매핑한다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, null, "text");

        // when
        TextAnswerMapper mapper = new TextAnswerMapper();
        TextAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual.getContent()).isEqualTo("text");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    void 텍스트_답변이_비어있는_경우_null로_매핑한다(String text) {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, null, text);

        // when
        TextAnswerMapper mapper = new TextAnswerMapper();
        TextAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual).isNull();
    }
}
