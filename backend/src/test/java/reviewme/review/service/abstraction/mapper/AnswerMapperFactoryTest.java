package reviewme.review.service.abstraction.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.abstraction.Answer;
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
    @DisplayName("지원하는 타입에 따른 매퍼를 가져온다.")
    void getBySupportingType() {
        // given
        List<NewAnswerMapper> answerMappers = List.of(answerMapper);
        AnswerMapperFactory factory = new AnswerMapperFactory(answerMappers);

        // when
        NewAnswerMapper actual = factory.getAnswerMapper(QuestionType.CHECKBOX);

        // then
        assertThat(answerMapper).isEqualTo(actual);
    }

    @Test
    @DisplayName("지원하지 않는 타입에 대한 매퍼 요청 시 예외가 발생한다.")
    void unsupportedQuestionType(CapturedOutput output) {
        // given
        AnswerMapperFactory factory = new AnswerMapperFactory(List.of());

        // when, then
        assertThatThrownBy(() -> factory.getAnswerMapper(QuestionType.TEXT))
                .isInstanceOf(UnsupportedQuestionTypeException.class);
        assertThat(output).contains("Unsupported", "TEXT");
    }
}
