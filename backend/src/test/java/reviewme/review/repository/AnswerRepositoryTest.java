package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 내가_받은_답변들_중_주어진_질문들에_대한_답변들을_최신_작성순으로_제한된_수만_반환한다() {
        // given
        long reviewGroupId = 1L;
        long templateId = 1L;

        TextAnswer answer1 = new TextAnswer(1L, "답1".repeat(20));
        TextAnswer answer2 = new TextAnswer(2L, "답2".repeat(20));
        TextAnswer answer3 = new TextAnswer(2L, "답3".repeat(20));
        TextAnswer answer4 = new TextAnswer(3L, "답4".repeat(20));
        reviewRepository.save(비회원_작성_리뷰(templateId, reviewGroupId, List.of(answer1)));
        reviewRepository.save(비회원_작성_리뷰(templateId, reviewGroupId, List.of(answer2)));
        reviewRepository.save(비회원_작성_리뷰(templateId, reviewGroupId, List.of(answer3)));

        // when
        List<Answer> actual = answerRepository.findReceivedAnswersByQuestionIds(
                reviewGroupId, List.of(1L, 2L), 2
        );

        // then
        assertThat(actual).containsExactly(answer3, answer2);
    }

    @Test
    void 리뷰_그룹_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        long reviewGroupId = 1L;
        TextAnswer answer1 = new TextAnswer(1L, "text answer1");
        TextAnswer answer2 = new TextAnswer(1L, "text answer2");
        Review review = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroupId, List.of(answer1, answer2)));

        // when
        Set<Long> actual = answerRepository.findIdsByReviewGroupId(reviewGroupId);

        // then
        assertThat(actual).containsExactly(answer1.getId(), answer2.getId());
    }

    @Test
    void 질문_id로_리뷰들을_찾아_id를_반환한다() {
        // given
        long reviewGroupId = 1L;
        TextAnswer textAnswer1_Q1 = new TextAnswer(1L, "text answer1 by Q1");
        TextAnswer textAnswer2_Q1 = new TextAnswer(1L, "text answer2 by Q1");
        TextAnswer textAnswer1_Q2 = new TextAnswer(2L, "text answer1 by Q2");

        reviewRepository.saveAll(List.of(
                비회원_작성_리뷰(1L, reviewGroupId, List.of(textAnswer1_Q1, textAnswer2_Q1)),
                비회원_작성_리뷰(1L, reviewGroupId, List.of(textAnswer1_Q2)
                )));

        // when
        Set<Long> actual = answerRepository.findIdsByQuestionId(1L);

        // then
        assertThat(actual).containsExactly(textAnswer1_Q1.getId(), textAnswer2_Q1.getId());
    }
}
