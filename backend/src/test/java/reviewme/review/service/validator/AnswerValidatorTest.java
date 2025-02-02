package reviewme.review.service.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.exception.QuestionNotContainingAnswersException;
import reviewme.review.service.exception.ReviewGroupNotContainingAnswersException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class AnswerValidatorTest {

    @Autowired
    private AnswerValidator answerValidator;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 답변이_질문에_속하는지_검증한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        Question question1 = 서술형_필수_질문();
        Question question2 = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question1, question2));
        templateRepository.save(new Template(List.of(section)));

        List<Answer> answers = List.of(
                new TextAnswer(question1.getId(), "답변1"),
                new TextAnswer(question2.getId(), "답변2")
        );
        Review review = reviewRepository.save(new Review(1L, reviewGroup.getId(), answers));
        Set<Long> answerIds = review.getAnsweredQuestionIds();
        List<Long> firstAnswerId = List.of(answers.get(0).getId());

        // when, then
        assertAll(
                () -> assertDoesNotThrow(
                        () -> answerValidator.validateQuestionContainsAnswers(question1.getId(), firstAnswerId)),
                () -> assertThatThrownBy(
                        () -> answerValidator.validateQuestionContainsAnswers(question1.getId(), answerIds))
                        .isInstanceOf(QuestionNotContainingAnswersException.class)
        );
    }

    @Test
    void 답변이_리뷰그룹에_속하는지_검증한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        Question question1 = 서술형_필수_질문();
        Question question2 = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question1, question2));
        templateRepository.save(new Template(List.of(section)));

        List<Answer> answers = List.of(
                new TextAnswer(question1.getId(), "답변1"),
                new TextAnswer(question2.getId(), "답변2")
        );
        Review review = reviewRepository.save(new Review(1L, reviewGroup.getId(), answers));

        List<Long> answerIds = review.getAnswers().stream().map(Answer::getQuestionId).toList();
        List<Long> wrongAnswerIds = new ArrayList<>(answerIds);
        wrongAnswerIds.add(Long.MAX_VALUE);

        // when, then
        assertAll(
                () -> assertDoesNotThrow(
                        () -> answerValidator.validateReviewGroupContainsAnswers(reviewGroup, answerIds)),
                () -> assertThatThrownBy(
                        () -> answerValidator.validateReviewGroupContainsAnswers(reviewGroup, wrongAnswerIds))
                        .isInstanceOf(ReviewGroupNotContainingAnswersException.class)
        );
    }
}
