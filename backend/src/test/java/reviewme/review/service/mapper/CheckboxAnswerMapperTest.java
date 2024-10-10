package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;

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

    @Test
    void 체크박스_답변_요청에_텍스트가_포함되어_있으면_예외를_발생시킨다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L, 2L, 3L), "text");

        // when
        CheckboxAnswerMapper mapper = new CheckboxAnswerMapper();

        // then
        assertThatThrownBy(() -> mapper.mapToAnswer(request))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }
}
