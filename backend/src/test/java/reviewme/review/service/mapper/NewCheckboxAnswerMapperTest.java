package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.NewCheckboxAnswer;
import reviewme.review.domain.NewCheckboxAnswerSelectedOption;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;

class NewCheckboxAnswerMapperTest {

    @Test
    void 체크박스_답변을_요청으로부터_매핑한다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L, 2L, 3L), null);
        NewCheckboxAnswerMapper mapper = new NewCheckboxAnswerMapper();

        // when
        NewCheckboxAnswer actual = mapper.mapToAnswer(request);

        // then
        assertThat(actual.getQuestionId()).isEqualTo(1L);
        assertThat(actual.getSelectedOptionIds())
                .extracting(NewCheckboxAnswerSelectedOption::getSelectedOptionId)
                .containsExactly(1L, 2L, 3L);
    }

    @Test
    void 체크박스_답변_요청에_텍스트가_포함되어_있으면_예외를_발생시킨다() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L, 2L, 3L), "text");

        // when
        NewCheckboxAnswerMapper mapper = new NewCheckboxAnswerMapper();

        // then
        assertThatThrownBy(() -> mapper.mapToAnswer(request))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }
}
