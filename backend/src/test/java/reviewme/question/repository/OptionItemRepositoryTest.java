package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
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
        long optionId1 = optionItemRepository.save(new OptionItem("1", 0, 1, OptionType.KEYWORD)).getId();
        long optionId2 = optionItemRepository.save(new OptionItem("2", 0, 1, OptionType.KEYWORD)).getId();
        long optionId3 = optionItemRepository.save(new OptionItem("3", 0, 1, OptionType.KEYWORD)).getId();
        long optionId4 = optionItemRepository.save(new OptionItem("4", 0, 1, OptionType.KEYWORD)).getId();
        optionItemRepository.save(new OptionItem("5", 0, 1, OptionType.KEYWORD));

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(1, List.of(optionId1, optionId2)),
                new CheckboxAnswer(2, List.of(optionId3, optionId4))
        );
        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        Set<Long> actual = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionId1, optionId2, optionId3, optionId4);
    }

    @Test
    void 리뷰_아이디와_질문_아이디로_선택한_옵션_아이템을_순서대로_불러온다() {
        // given
        long optionId1 = optionItemRepository.save(new OptionItem("1", 0, 3, OptionType.KEYWORD)).getId();
        long optionId2 = optionItemRepository.save(new OptionItem("2", 0, 2, OptionType.KEYWORD)).getId();
        long optionId3 = optionItemRepository.save(new OptionItem("3", 0, 1, OptionType.KEYWORD)).getId();
        long optionId4 = optionItemRepository.save(new OptionItem("4", 0, 1, OptionType.KEYWORD)).getId();
        optionItemRepository.save(new OptionItem("5", 0, 1, OptionType.KEYWORD));

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(1, List.of(optionId1, optionId3)),
                new CheckboxAnswer(2, List.of(optionId4))
        );
        Question question1 = questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문", null, 1));
        questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문", null, 1));

        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), question1.getId()
        );

        // then
        assertThat(actual).extracting(OptionItem::getId).containsExactly(optionId3, optionId1);
    }

}
