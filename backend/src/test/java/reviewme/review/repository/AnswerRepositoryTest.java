package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void 리뷰_그룹_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹());
        TextAnswer answer1 = new TextAnswer(1L, "text answer1");
        TextAnswer answer2 = new TextAnswer(1L, "text answer2");
        Review review = reviewRepository.save(new Review(1L, reviewGroup.getId(), List.of(answer1, answer2)));

        // when
        Set<Long> actual = answerRepository.findIdsByReviewGroupId(reviewGroup.getId());

        // then
        assertThat(actual).containsExactly(answer1.getId(), answer2.getId());
    }

    @Test
    void 질문_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹());
        long questionId1 = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long questionId2 = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        TextAnswer textAnswer1_Q1 = new TextAnswer(questionId1, "text answer1 by Q1");
        TextAnswer textAnswer2_Q1 = new TextAnswer(questionId1, "text answer2 by Q1");
        TextAnswer textAnswer1_Q2 = new TextAnswer(questionId2, "text answer1 by Q2");

        reviewRepository.saveAll(List.of(
                new Review(1L, reviewGroup.getId(), List.of(textAnswer1_Q1, textAnswer2_Q1)),
                new Review(1L, reviewGroup.getId(), List.of(textAnswer1_Q2)
                )));

        // when
        Set<Long> actual = answerRepository.findIdsByQuestionId(questionId1);

        // then
        assertThat(actual).containsExactly(textAnswer1_Q1.getId(), textAnswer2_Q1.getId());
    }
}
