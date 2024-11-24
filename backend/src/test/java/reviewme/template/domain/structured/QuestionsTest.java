package reviewme.template.domain.structured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.exception.QuestionNotExistException;
import reviewme.template.repository.QuestionRepository;

@ServiceTest
class QuestionsTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 존재하지_않는_질문으로_생성할_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Questions(null)).isInstanceOf(QuestionNotExistException.class),
                () -> assertThatThrownBy(() -> new Questions(List.of())).isInstanceOf(QuestionNotExistException.class)
        );
    }

    @Test
    void 질문ID들을_반환한다() {
        // given
        Question question1 = questionRepository.save(서술형_필수_질문());
        Question question2 = questionRepository.save(서술형_필수_질문());
        Question question3 = questionRepository.save(서술형_필수_질문());

        Questions questions = new Questions(List.of(question1, question2, question3));

        // when
        List<Long> actual = questions.getQuestionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(question1.getId(), question2.getId(), question3.getId());
    }

    @Test
    void 체크박스형_질문ID들을_반환한다() {
        // given
        Question textQuestion = questionRepository.save(서술형_필수_질문());
        Question checkboxQuestion1 = questionRepository.save(선택형_필수_질문());
        Question checkboxQuestion2 = questionRepository.save(선택형_필수_질문());

        Questions questions = new Questions(List.of(textQuestion, checkboxQuestion1, checkboxQuestion2));

        // when
        List<Long> actual = questions.getCheckboxQuestionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(checkboxQuestion1.getId(), checkboxQuestion2.getId());
    }

    @Test
    void 체크박스_질문이_있는지_확인한다() {
        // given
        Question textQuestion = questionRepository.save(서술형_필수_질문());
        Question checkboxQuestion1 = questionRepository.save(선택형_필수_질문());
        Question checkboxQuestion2 = questionRepository.save(선택형_필수_질문());

        Questions questions1 = new Questions(List.of(textQuestion, checkboxQuestion1, checkboxQuestion2));
        Questions questions2 = new Questions(List.of(textQuestion));

        // when
        boolean actual1 = questions1.hasCheckboxQuestion();
        boolean actual2 = questions2.hasCheckboxQuestion();

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
