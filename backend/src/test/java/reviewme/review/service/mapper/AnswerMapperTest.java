package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;
import reviewme.support.ServiceTest;

@ServiceTest
class AnswerMapperTest {

    @Autowired
    private AnswerMapper answerMapper;

    @Test
    void 답변_요청을_서술형_답변으로_매핑한다() {
        // given
        long questionId = 1L;
        String text = "답변";
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, null, text);

        // when
        TextAnswer textAnswer = answerMapper.mapToTextAnswer(answerRequest);

        // then
        assertAll(
                () -> assertThat(textAnswer.getQuestionId()).isEqualTo(questionId),
                () -> assertThat(textAnswer.getContent()).isEqualTo(text)
        );
    }

    @Test
    void 답변_요청을_선택형_답변으로_매핑한다() {
        // given
        long questionId = 1L;
        long selectedOptionsId = 2L;
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, List.of(selectedOptionsId), null);

        // when
        CheckboxAnswer checkboxAnswer = answerMapper.mapToCheckBoxAnswer(answerRequest);

        // then
        assertAll(
                () -> assertThat(checkboxAnswer.getQuestionId()).isEqualTo(questionId),
                () -> assertThat(checkboxAnswer.getSelectedOptionIds())
                        .extracting(CheckBoxAnswerSelectedOption::getSelectedOptionId)
                        .containsOnly(selectedOptionsId)
        );
    }

    @Test
    void 서술형_답변_매핑시_선택형_답변이_존재할_경우_예외가_발생한다() {
        // given
        long questionId = 1L;
        String text = "답변";
        long selectedOptionsId = 2L;
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, List.of(selectedOptionsId), text);

        // when, then
        assertThatThrownBy(() -> answerMapper.mapToTextAnswer(answerRequest))
                .isInstanceOf(TextAnswerIncludedOptionItemException.class);
    }

    @Test
    void 선택형_답변_매핑시_서술형_답변이_존재할_경우_예외가_발생한다() {
        // given
        long questionId = 1L;
        String text = "답변";
        long selectedOptionsId = 2L;
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, List.of(selectedOptionsId), text);

        // when, then
        assertThatThrownBy(() -> answerMapper.mapToCheckBoxAnswer(answerRequest))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }
}
