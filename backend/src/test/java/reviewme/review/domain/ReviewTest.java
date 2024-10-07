package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}
