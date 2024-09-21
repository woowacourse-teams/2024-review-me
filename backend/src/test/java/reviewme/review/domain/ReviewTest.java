package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void 리뷰에_등록된_답변의_모든_질문들을_반환한다() {
        // given
        TextAnswer textAnswer = new TextAnswer(1L, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(2L, List.of(1L));
        Review review = new Review(1L, 1L, List.of(textAnswer, checkboxAnswer));

        // when
        Set<Long> allQuestionIdsFromAnswers = review.getAnsweredQuestionIds();

        // then
        assertThat(allQuestionIdsFromAnswers).containsAll(List.of(1L, 2L));
    }

    @Test
    void 리뷰에_특정_질문에_대한_답변이_있는지_여부를_반환한다() {
        // given
        long textQuestionId = 1L;
        long checkBoxQuestionId = 2L;

        TextAnswer textAnswer = new TextAnswer(textQuestionId, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkBoxQuestionId, List.of(1L));
        Review review = new Review(1L, 1L, List.of(textAnswer, checkboxAnswer));

        // when, then
        assertAll(
                () -> assertThat(review.hasAnsweredQuestion(textQuestionId)).isTrue(),
                () -> assertThat(review.hasAnsweredQuestion(checkBoxQuestionId)).isTrue()
        );
    }
}
