package reviewme.review.service.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class TextTypedAnswerValidatorTest {

    @Autowired
    private TextTypedAnswerValidator textAnswerValidator;

    @Autowired
    private TemplateRepository templateRepository;

    // TODO: Answer의 검증을 위해 Template의 저장이 필요하다. (Question의 제한), 조금 더 위쪽 레이어에서 명세를 활용해서 처리해도 좋겠다.
    @Test
    void 저장되지_않은_질문에_대한_대답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        TextAnswer textAnswer = new TextAnswer(notSavedQuestionId, "텍스트형 응답");

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 10001})
    void 필수_질문의_답변_길이가_유효하지_않으면_예외가_발생한다(int length) {
        // given
        String content = "답".repeat(length);
        Question savedQuestion = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        TextAnswer textAnswer = new TextAnswer(savedQuestion.getId(), content);

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문의_답변_길이가_유효하지_않으면_예외가_발생한다() {
        // given
        String content = "답".repeat(10001);
        Question savedQuestion = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        TextAnswer textAnswer = new TextAnswer(savedQuestion.getId(), content);

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문은_최소_글자수_제한을_받지_않는다() {
        // given
        String content = "답".repeat(1);
        Question savedQuestion = 서술형_옵션_질문();
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        TextAnswer textAnswer = new TextAnswer(savedQuestion.getId(), content);

        // when, then
        assertDoesNotThrow(() -> textAnswerValidator.validate(textAnswer));
    }
}
