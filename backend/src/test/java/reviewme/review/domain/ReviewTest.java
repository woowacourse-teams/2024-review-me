package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReviewTest {

    private static final Logger log = LoggerFactory.getLogger(ReviewTest.class);

    @Test
    void 리뷰에_등록된_답변의_모든_질문들을_반환한다() {
        // given
        TextAnswer textAnswer = new TextAnswer(1L, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(2L, List.of(1L));
        Review review = new Review(1L, 1L, List.of(textAnswer), List.of(checkboxAnswer));

        // when
        Set<Long> allQuestionIdsFromAnswers = review.getAnsweredQuestionIds();

        // then
        assertThat(allQuestionIdsFromAnswers).containsAll(List.of(1L, 2L));
    }

    @Test
    void 리뷰에_등록된_모든_선택형_답변의_옵션들을_반환환다() {
        // given
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer(1L, List.of(1L, 2L));
        CheckboxAnswer checkboxAnswer2 = new CheckboxAnswer(1L, List.of(3L, 4L));
        Review review = new Review(1L, 1L, List.of(), List.of(checkboxAnswer1, checkboxAnswer2));

        // when
        Set<Long> allQuestionIdsFromAnswers = review.getAllCheckBoxOptionIds();

        // then
        assertThat(allQuestionIdsFromAnswers).containsAll(List.of(1L, 2L, 3L, 4L));
    }

    @Test
    void 리뷰에_특정_질문에_대한_답변이_있는지_여부를_반환한다() {
        // given
        long textQuestionId = 1L;
        long checkBoxQuestionId = 2L;

        TextAnswer textAnswer = new TextAnswer(textQuestionId, "답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkBoxQuestionId, List.of(1L));
        Review review = new Review(1L, 1L, List.of(textAnswer), List.of(checkboxAnswer));

        // when, then
        assertAll(
                () -> assertThat(review.hasAnsweredQuestion(textQuestionId)).isTrue(),
                () -> assertThat(review.hasAnsweredQuestion(checkBoxQuestionId)).isTrue()
        );
    }
}
