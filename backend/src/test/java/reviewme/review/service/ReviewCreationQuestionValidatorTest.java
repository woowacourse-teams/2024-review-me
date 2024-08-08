package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixure.기술역량이_어떤가요;
import static reviewme.fixture.QuestionFixure.소프트스킬이_어떤가요;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.exception.DuplicateQuestionException;
import reviewme.review.repository.QuestionRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewCreationQuestionValidatorTest {

    @Autowired
    private ReviewCreationQuestionValidator reviewCreationQuestionValidator;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 중복되는_아이디의_질문인지_검사한다() {
        // given
        long questionId1 = questionRepository.save(소프트스킬이_어떤가요.create()).getId();
        long questionId2 = questionRepository.save(기술역량이_어떤가요.create()).getId();

        // when, then
        assertAll(
                () -> assertThatCode(() -> reviewCreationQuestionValidator.validate(List.of(questionId1, questionId2)))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> reviewCreationQuestionValidator.validate(List.of(questionId1, questionId1)))
                        .isInstanceOf(DuplicateQuestionException.class)
        );
    }
}
