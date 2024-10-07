package reviewme.review.domain.abstraction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.NewReview;
import reviewme.review.domain.NewTextAnswer;

class NewReviewTest {

    @Test
    void 리뷰에_등록된_답변의_모든_질문들을_반환한다() {
        // given
        NewTextAnswer textAnswer = new NewTextAnswer(1L, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(2L, List.of(1L));
        NewReview review = new NewReview(1L, 1L, List.of(textAnswer, checkboxAnswer));

        // when
        Set<Long> allQuestionIdsFromAnswers = review.getAnsweredQuestionIds();

        // then
        assertThat(allQuestionIdsFromAnswers).containsAll(List.of(1L, 2L));
    }

    @Test
    void 리뷰에_등록된_타입에_따라_답변을_반환한다() {
        // given
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer(1L, List.of(1L, 2L));
        CheckboxAnswer checkboxAnswer2 = new CheckboxAnswer(1L, List.of(3L, 4L));
        NewTextAnswer textAnswer = new NewTextAnswer(1L, "답변");
        NewReview review = new NewReview(1L, 1L, List.of(checkboxAnswer1, checkboxAnswer2, textAnswer));

        // when
        List<CheckboxAnswer> allQuestionIdsFromAnswers = review.getAnswersByType(CheckboxAnswer.class);

        // then
        assertThat(allQuestionIdsFromAnswers).containsAll(List.of(checkboxAnswer1, checkboxAnswer2));
    }

    @Test
    void 리뷰에_특정_질문에_대한_답변이_있는지_여부를_반환한다() {
        // given
        long textQuestionId = 1L;
        long checkBoxQuestionId = 2L;

        NewTextAnswer textAnswer = new NewTextAnswer(textQuestionId, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkBoxQuestionId, List.of(1L));
        NewReview review = new NewReview(1L, 1L, List.of(textAnswer, checkboxAnswer));

        // when, then
        assertAll(
                () -> assertThat(review.hasAnsweredQuestion(textQuestionId)).isTrue(),
                () -> assertThat(review.hasAnsweredQuestion(checkBoxQuestionId)).isTrue()
        );
    }
}
