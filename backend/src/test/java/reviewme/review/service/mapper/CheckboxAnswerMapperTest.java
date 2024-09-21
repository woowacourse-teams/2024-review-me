package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.support.ServiceTest;

@ServiceTest
class CheckboxAnswerMapperTest {

    @Autowired
    private CheckboxAnswerMapper checkboxAnswerMapper;

    @Test
    void 답변_요청을_선택형_답변으로_매핑한다() {
        // given
        long questionId = 1L;
        long selectedOptionsId = 2L;
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, List.of(selectedOptionsId), null);

        // when
        CheckboxAnswer checkboxAnswer = checkboxAnswerMapper.mapToAnswer(answerRequest);

        // then
        assertAll(
                () -> assertThat(checkboxAnswer.getQuestionId()).isEqualTo(questionId),
                () -> assertThat(checkboxAnswer.getSelectedOptionIds())
                        .extracting(CheckBoxAnswerSelectedOption::getSelectedOptionId)
                        .containsOnly(selectedOptionsId)
        );
    }

    @Test
    void 선택형_답변_매핑시_다른_타입의_답변이_존재할_경우_예외가_발생한다() {
        // given
        long questionId = 1L;
        String text = "답변";
        long selectedOptionsId = 2L;
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, List.of(selectedOptionsId), text);

        // when, then
        assertThatThrownBy(() -> checkboxAnswerMapper.mapToAnswer(answerRequest))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }
}
