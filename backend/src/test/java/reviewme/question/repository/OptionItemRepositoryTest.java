package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.OptionItemFixture.꼬리_질문_선택지;
import static reviewme.fixture.OptionItemFixture.카테고리_선택지;
import static reviewme.fixture.QuestionFixture.꼬리_질문_선택형;
import static reviewme.fixture.QuestionFixture.카테고리_질문_선택형;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 리뷰_아이디로_선택한_옵션_아이템_아이디를_불러온다() {
        // given
        long categoryOptionItemId1 = optionItemRepository.save(카테고리_선택지.create()).getId();
        long categoryOptionItemId2 = optionItemRepository.save(카테고리_선택지.create()).getId();
        long followingOptionItemId1 = optionItemRepository.save(꼬리_질문_선택지.create()).getId();
        optionItemRepository.save(꼬리_질문_선택지.create());

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(1, List.of(categoryOptionItemId1, categoryOptionItemId2)),
                new CheckboxAnswer(2, List.of(followingOptionItemId1))
        );
        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        Set<Long> actual = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());

        // then
        assertThat(actual)
                .containsExactlyInAnyOrder(categoryOptionItemId1, categoryOptionItemId2, followingOptionItemId1);
    }

    @Test
    void 리뷰_아이디와_질문_아이디로_선택한_옵션_아이템을_position_순서대로_불러온다() {
        // given
        long categoryOptionItemId1 = optionItemRepository.save(카테고리_선택지.createWithPosition(1)).getId();
        long categoryOptionItemId2 = optionItemRepository.save(카테고리_선택지.createWithPosition(2)).getId();
        long followingOptionItemId1 = optionItemRepository.save(꼬리_질문_선택지.createWithPosition(1)).getId();
        optionItemRepository.save(꼬리_질문_선택지.createWithPosition(2));
        long followingOptionItemId3 = optionItemRepository.save(꼬리_질문_선택지.createWithPosition(3)).getId();

        Question categoryQuestion = questionRepository.save(카테고리_질문_선택형.create());
        Question followingQuestion = questionRepository.save(꼬리_질문_선택형.create());
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(categoryQuestion.getId(), List.of(categoryOptionItemId1, categoryOptionItemId2)),
                new CheckboxAnswer(followingQuestion.getId(), List.of(followingOptionItemId1, followingOptionItemId3))
        );

        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), followingQuestion.getId()
        );

        // then
        assertThat(actual)
                .extracting(OptionItem::getId)
                .containsExactly(followingOptionItemId1, followingOptionItemId3);
    }
}
