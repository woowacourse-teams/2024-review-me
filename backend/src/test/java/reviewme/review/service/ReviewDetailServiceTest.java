package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.ReviewRepository2;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewDetailServiceTest {

    @Autowired
    private ReviewDetailService reviewDetailService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository2 reviewRepository2;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository2 questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 사용자가_작성한_리뷰를_확인한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup("aru", "reviewme", "ABCD", "0000"));
        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.TEXT, "질문", null, 1));
        Question2 question2 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));
        Question2 question3 = questionRepository.save(new Question2(true, QuestionType.TEXT, "체크 1 조건", "가이드라인", 1));
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 3));
        OptionItem optionItem1 = optionItemRepository.save(new OptionItem("체크 1", optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(new OptionItem("체크 2", optionGroup.getId(), 1));

        Section section1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId(), question2.getId()), null, "1번 섹션", 1)
        );
        Section section2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question3.getId()), optionItem1.getId(), "2번 섹션", 2)
        );
        Template template = templateRepository.save(new Template(List.of(section1.getId(), section2.getId())));

        List<TextAnswer> textAnswers = List.of(
                new TextAnswer(1, "질문 1 답변"),
                new TextAnswer(3, "질문 3 답변")
        );
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(2, List.of(optionItem1.getId(), optionItem2.getId()))
        );
        Review2 review = reviewRepository2.save(
                new Review2(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );

        // when
        TemplateAnswerResponse reviewDetail = reviewDetailService.getReviewDetail("0000", review.getId());

        // then
        assertThat(reviewDetail.sections()).hasSize(2);
    }

    @Test
    void 섹션을_보이게_하는_옵션을_선택하지_않은_경우_해당_섹션을_제외하고_보여준다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup("aru", "reviewme", "ABCD", "0000"));
        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.TEXT, "질문", null, 1));
        Question2 question2 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 2));
        Question2 question3 = questionRepository.save(new Question2(true, QuestionType.TEXT, "체크 1 조건", "가이드라인", 3));
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 3));
        OptionItem optionItem1 = optionItemRepository.save(new OptionItem("체크 1", optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(new OptionItem("체크 2", optionGroup.getId(), 2));

        Section section1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId(), question2.getId()), null, "1번 섹션", 1)
        );
        Section section2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question3.getId()), optionItem1.getId(), "2번 섹션", 2)
        );
        Template template = templateRepository.save(new Template(List.of(section1.getId(), section2.getId())));

        List<TextAnswer> textAnswers = List.of(new TextAnswer(question1.getId(), "질문 1 답변"));
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question2.getId(), List.of(optionItem2.getId())));
        Review2 review = reviewRepository2.save(
                new Review2(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );

        // when
        TemplateAnswerResponse reviewDetail = reviewDetailService.getReviewDetail("0000", review.getId());

        // then
        assertThat(reviewDetail.sections()).hasSize(1);
    }
}
