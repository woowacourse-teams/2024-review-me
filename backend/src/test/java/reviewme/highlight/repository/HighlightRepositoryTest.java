package reviewme.highlight.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightRange;
import reviewme.review.domain.Answer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@DataJpaTest
class HighlightRepositoryTest {

    @Autowired
    private HighlightRepository highlightRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 한_번에_여러_하이라이트를_벌크_삽입한다() {
        // given
        List<Highlight> highlights = List.of(
                new Highlight(1L, 1, new HighlightRange(1, 2)),
                new Highlight(1L, 1, new HighlightRange(3, 5))
        );

        // when
        highlightRepository.saveAll(highlights);

        // then
        List<Highlight> actual = highlightRepository.findAllByAnswerIdsOrderedAsc(List.of(1L));
        assertThat(actual)
                .extracting(Highlight::getHighlightRange)
                .containsExactly(new HighlightRange(1, 2), new HighlightRange(3, 5));
    }

    @Test
    void 하이라이트를_줄번호_시작_인덱스_순서대로_정렬해서_가져온다() {
        // given
        highlightRepository.saveAll(
                List.of(
                        new Highlight(1L, 1, new HighlightRange(1, 2)),
                        new Highlight(1L, 2, new HighlightRange(6, 7)),
                        new Highlight(1L, 2, new HighlightRange(2, 3)),
                        new Highlight(1L, 3, new HighlightRange(3, 4)),
                        new Highlight(1L, 1, new HighlightRange(4, 5)),
                        new Highlight(2L, 3, new HighlightRange(7, 8))
                )
        );
        // 1: (1, 2), (4, 5) 2: (2, 3), (6, 7) 3: (3, 4) -> 1 4 2 6 3

        // when
        List<Highlight> actual = highlightRepository.findAllByAnswerIdsOrderedAsc(List.of(1L));

        // then
        assertAll(
                () -> assertThat(actual).extracting(Highlight::getLineIndex)
                        .containsExactly(1, 1, 2, 2, 3),
                () -> assertThat(actual)
                        .extracting(Highlight::getHighlightRange)
                        .extracting(HighlightRange::getStartIndex)
                        .containsExactly(1, 4, 2, 6, 3)
        );
    }

    @Test
    void 그룹_아이디와_질문_아이디로_하이라이트를_삭제한다() {
        // given
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(리뷰_그룹());
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(리뷰_그룹());

        List<Answer> answers1 = List.of(
                new TextAnswer(1L, "A1"),
                new TextAnswer(2L, "A2"),
                new TextAnswer(3L, "A3")
        );
        List<Answer> answers2 = List.of(
                new TextAnswer(1L, "B1"),
                new TextAnswer(2L, "B2"),
                new TextAnswer(3L, "B3")
        );
        reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup1.getId(), answers1));
        reviewRepository.save(비회원_작성_리뷰(2L, reviewGroup2.getId(), answers2));

        List<Long> answerIds = new ArrayList<>();
        answerIds.addAll(answers1.stream().map(Answer::getId).toList());
        answerIds.addAll(answers2.stream().map(Answer::getId).toList());

        HighlightRange range = new HighlightRange(0, 1);
        answerIds.stream()
                .map(answerId -> new Highlight(answerId, 0, range))
                .forEach(highlightRepository::save);

        // when
        highlightRepository.deleteByReviewGroupIdAndQuestionId(reviewGroup1.getId(), 1L);

        // then
        List<Highlight> actual = highlightRepository.findAllByAnswerIdsOrderedAsc(answerIds);
        assertAll(
                () -> assertThat(actual).hasSize(5),
                () -> assertThat(actual).extracting(Highlight::getAnswerId).doesNotContain(answers1.get(0).getId())
        );
    }
}
