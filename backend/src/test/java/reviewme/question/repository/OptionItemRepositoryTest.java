package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

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
        Question question1 = questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문1", null, 1));
        Question question2 = questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문2", null, 2));

        long optionGroupId = optionGroupRepository.save(new OptionGroup(question1.getId(), 1, 3)).getId();
        long optionId1 = optionItemRepository.save(new OptionItem("1", optionGroupId, 3, OptionType.KEYWORD)).getId();
        long optionId2 = optionItemRepository.save(new OptionItem("2", optionGroupId, 2, OptionType.KEYWORD)).getId();
        long optionId3 = optionItemRepository.save(new OptionItem("3", optionGroupId, 1, OptionType.KEYWORD)).getId();
        long optionId4 = optionItemRepository.save(new OptionItem("4", optionGroupId, 1, OptionType.KEYWORD)).getId();
        long optionId5 = optionItemRepository.save(new OptionItem("5", optionGroupId, 1, OptionType.KEYWORD)).getId();

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question1.getId(), List.of(optionId1, optionId3)),
                new CheckboxAnswer(question2.getId(), List.of(optionId4))
        );

        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), question1.getId()
        );

        // then
        assertThat(actual).extracting(OptionItem::getId).containsExactly(optionId3, optionId1);
    }
}
