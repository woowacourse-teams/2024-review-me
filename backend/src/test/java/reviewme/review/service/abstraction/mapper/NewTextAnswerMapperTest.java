package reviewme.review.service.abstraction.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.abstraction.NewTextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;

class NewTextAnswerMapperTest {

    /*
     TODO: Request를 추상화해야 할까요?
     떠오르는 방법은 아래와 같습니다.
     1: static factory method를 사용 -> 걷잡을 수 없어지지 않을까요?
     2: 다른 방식으로 추상화 ?
     */

    @Test
    void 텍스트_답변을_요청으로부터_매핑한다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, null, "text");

        // when
        NewTextAnswerMapper mapper = new NewTextAnswerMapper();
        NewTextAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual.getContent()).isEqualTo("text");
    }

    @Test
    void 텍스트_답변_요청에_옵션이_포함되어_있으면_예외를_발생시킨다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L), "text");

        // when
        NewTextAnswerMapper mapper = new NewTextAnswerMapper();

        // then
        assertThatThrownBy(() -> mapper.mapToAnswer(request))
                .isInstanceOf(TextAnswerIncludedOptionItemException.class);
    }
}
