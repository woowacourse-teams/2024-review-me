package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;
import reviewme.support.ServiceTest;

@ServiceTest
class TextAnswerMapperTest {

    @Autowired
    private TextAnswerMapper textAnswerMapper;

    @Test
    void 답변_요청을_서술형_답변으로_매핑한다() {
        // given
        long questionId = 1L;
        String text = "답변";
        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(questionId, null, text);

        // when
        TextAnswer textAnswer = textAnswerMapper.mapToAnswer(answerRequest);

        // then
        assertAll(
                () -> assertThat(textAnswer.getQuestionId()).isEqualTo(questionId),
                () -> assertThat(textAnswer.getContent()).isEqualTo(text)
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
        assertThatThrownBy(() -> textAnswerMapper.mapToAnswer(answerRequest))
                .isInstanceOf(TextAnswerIncludedOptionItemException.class);
    }
}
