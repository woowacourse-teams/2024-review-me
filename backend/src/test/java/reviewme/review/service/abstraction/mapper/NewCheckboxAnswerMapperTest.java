package reviewme.review.service.abstraction.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.abstraction.NewCheckboxAnswer;
import reviewme.review.domain.abstraction.NewCheckboxAnswerSelectedOption;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;

class NewCheckboxAnswerMapperTest {

    @Test
    @DisplayName("체크박스 답변을 요청으로부터 매핑한다.")
    void mapCheckboxAnswerFromRequest() {
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
    @DisplayName("체크박스 답변 요청에 텍스트가 포함되어 있으면 예외를 발생시킨다.")
    void invalidRequest() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L, 2L, 3L), "text");

        // when
        NewCheckboxAnswerMapper mapper = new NewCheckboxAnswerMapper();

        // then
        assertThatThrownBy(() -> mapper.mapToAnswer(request))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }
}
