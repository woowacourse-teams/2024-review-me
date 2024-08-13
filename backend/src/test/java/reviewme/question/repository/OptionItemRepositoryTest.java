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
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.ReviewRepository2;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository2 reviewRepository;

    @Autowired
    private QuestionRepository2 questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TemplateRepository templateRepository;

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
        Review2 review = reviewRepository.save(new Review2(0, 0, List.of(), checkboxAnswers));

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

        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));
        Question2 question2 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question1.getId(), List.of(optionId1, optionId3)),
                new CheckboxAnswer(question2.getId(), List.of(optionId4))
        );

        Review2 review = reviewRepository.save(new Review2(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), question1.getId()
        );

        // then
        assertThat(actual).extracting(OptionItem::getId).containsExactly(optionId3, optionId1);
    }

    @Test
    void 리뷰_아이디와_옵션_타입으로_선택한_옵션_아이템을_불러온다() {
        // given
        String groupAccessCode = "groupAccessCode";
        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX,
                "프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)", null, 1));
        Question2 question2 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX,
                "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요. (1개 이상)", null, 1));

        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question1.getId(), 1, 2));
        OptionGroup keywordOptionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 10));

        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        new OptionItem("문제해결 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        OptionItem keywordOption = new OptionItem("얘기를 잘 들어줘요", keywordOptionGroup.getId(), 2, OptionType.KEYWORD);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2, keywordOption));

        Template template = templateRepository.save(new Template(List.of()));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", "reviewRequestCode", groupAccessCode)
        );
        CheckboxAnswer categoryAnswer = new CheckboxAnswer(
                question1.getId(), List.of(categoryOption1.getId(), categoryOption2.getId())
        );
        CheckboxAnswer keywordAnswer = new CheckboxAnswer(question2.getId(), List.of(keywordOption.getId()));
        Review2 review = reviewRepository.save(
                new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer, keywordAnswer))
        );

        // when
        List<OptionItem> optionItems = optionItemRepository.findByOptionTypeAndReviewId(
                review.getId(), OptionType.CATEGORY
        );

        // then
        assertAll(
                () -> assertThat(optionItems).hasSize(2),
                () -> assertThat(optionItems).allSatisfy(
                        optionItem -> assertThat(optionItem.getOptionType()).isEqualTo(OptionType.CATEGORY))
        );
    }
}
