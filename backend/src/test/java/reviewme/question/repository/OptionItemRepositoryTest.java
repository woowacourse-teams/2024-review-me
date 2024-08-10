package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.ReviewRepository2;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository2 reviewRepository;

    @Autowired
    private QuestionRepository2 questionRepository;

    @Test
    void 리뷰_아이디로_선택한_옵션_아이템_아이디를_불러온다() {
        // given
        long optionId1 = optionItemRepository.save(new OptionItem("1", 0, 1)).getId();
        long optionId2 = optionItemRepository.save(new OptionItem("2", 0, 1)).getId();
        long optionId3 = optionItemRepository.save(new OptionItem("3", 0, 1)).getId();
        long optionId4 = optionItemRepository.save(new OptionItem("4", 0, 1)).getId();
        optionItemRepository.save(new OptionItem("5", 0, 1));

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(1, List.of(optionId1, optionId2)),
                new CheckboxAnswer(2, List.of(optionId3, optionId4))
        );
        Review2 review = reviewRepository.save(new Review2(0, 0, List.of(), checkboxAnswers));

        // when
        Set<Long> actual = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionId1, optionId2, optionId3, optionId4);
    }

    @Test
    void 리뷰_아이디와_질문_아이디로_선택한_옵션_아이템을_순서대로_불러온다() {
        // given
        long optionId1 = optionItemRepository.save(new OptionItem("1", 0, 3)).getId();
        long optionId2 = optionItemRepository.save(new OptionItem("2", 0, 2)).getId();
        long optionId3 = optionItemRepository.save(new OptionItem("3", 0, 1)).getId();
        long optionId4 = optionItemRepository.save(new OptionItem("4", 0, 1)).getId();
        optionItemRepository.save(new OptionItem("5", 0, 1));

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(1, List.of(optionId1, optionId3)),
                new CheckboxAnswer(2, List.of(optionId4))
        );
        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));
        questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));

        Review2 review = reviewRepository.save(new Review2(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), question1.getId()
        );

        // then
        assertThat(actual).extracting(OptionItem::getId).containsExactly(optionId3, optionId1);
    }

}
