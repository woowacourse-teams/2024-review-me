package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

class CheckboxAnswerMapperTest {

    @Test
    void 체크박스_답변을_요청으로부터_매핑한다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L, 2L, 3L), null);
        CheckboxAnswerMapper mapper = new CheckboxAnswerMapper();

        // when
        CheckboxAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual.getQuestionId()).isEqualTo(1L);
        assertThat(actual.getSelectedOptionIds())
                .extracting(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .containsExactly(1L, 2L, 3L);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 체크박스_답변이_비어있는_경우_null로_매핑한다(List<Long> selectedOptionIds) {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, selectedOptionIds, null);
        CheckboxAnswerMapper mapper = new CheckboxAnswerMapper();

        // when
        CheckboxAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual).isNull();
    }
}
