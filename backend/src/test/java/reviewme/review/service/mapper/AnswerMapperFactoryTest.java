package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

@ExtendWith(OutputCaptureExtension.class)
class AnswerMapperFactoryTest {

    private final NewAnswerMapper answerMapper = new NewAnswerMapper() {

        @Override
        public boolean supports(QuestionType questionType) {
            return questionType == QuestionType.CHECKBOX;
        }

        @Override
        public Answer mapToAnswer(ReviewAnswerRequest answerRequest) {
            return null;
        }
    };

    @Test
    void 지원하는_타입에_따른_매퍼를_가져온다() {
        // given
        List<NewAnswerMapper> answerMappers = List.of(answerMapper);
        AnswerMapperFactory factory = new AnswerMapperFactory(answerMappers);

        // when
        NewAnswerMapper actual = factory.getAnswerMapper(QuestionType.CHECKBOX);

        // then
        assertThat(answerMapper).isEqualTo(actual);
    }

    @Test
    void 지원하지_않는_타입에_대한_매퍼_요청_시_예외가_발생한다(CapturedOutput output) {
        // given
        AnswerMapperFactory factory = new AnswerMapperFactory(List.of());

        // when, then
        assertThatThrownBy(() -> factory.getAnswerMapper(QuestionType.TEXT))
                .isInstanceOf(UnsupportedQuestionTypeException.class);
        assertThat(output).contains("Unsupported", "TEXT");
    }
}
