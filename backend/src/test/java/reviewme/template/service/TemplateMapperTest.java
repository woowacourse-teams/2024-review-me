package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.domain.exception.MissingOptionItemsInOptionGroupException;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.domain.exception.SectionNotFoundException;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class TemplateMapperTest {

    @Autowired
    TemplateMapper templateMapper;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    @Autowired
    OptionItemRepository optionItemRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰_그룹과_템플릿으로_템플릿_응답을_매핑한다() {
        // given
        Question question1 = new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1);
        Question question2 = new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1);
        questionRepository.saveAll(List.of(question1, question2));

        OptionGroup optionGroup = new OptionGroup(question2.getId(), 1, 2);
        optionGroupRepository.save(optionGroup);

        OptionItem optionItem = new OptionItem("선택지", optionGroup.getId(), 1, OptionType.CATEGORY);
        optionItemRepository.save(optionItem);

        Section section1 = new Section(VisibleType.ALWAYS, List.of(question1.getId()), null, "말머리1", 1);
        Section section2 = new Section(VisibleType.ALWAYS, List.of(question2.getId()), null, "말머리2", 2);
        sectionRepository.saveAll(List.of(section1, section2));

        Template template = new Template(List.of(section1.getId(), section2.getId()));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when
        TemplateResponse templateResponse = templateMapper.mapToTemplateResponse(reviewGroup, template);

        // then
        assertAll(
                () -> assertThat(templateResponse.revieweeName()).isEqualTo(reviewGroup.getReviewee()),
                () -> assertThat(templateResponse.projectName()).isEqualTo(reviewGroup.getProjectName()),
                () -> assertThat(templateResponse.sections()).hasSize(2),
                () -> assertThat(templateResponse.sections().get(0).header()).isEqualTo(section1.getHeader()),
                () -> assertThat(templateResponse.sections().get(0).questions()).hasSize(1),
                () -> assertThat(templateResponse.sections().get(1).header()).isEqualTo(section2.getHeader()),
                () -> assertThat(templateResponse.sections().get(1).questions()).hasSize(1)
        );
    }

    @Test
    void 섹션의_선택된_옵션이_필요없는_경우_제공하지_않는다() {
        // given
        Question question = new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1);
        questionRepository.save(question);

        Section section = new Section(VisibleType.ALWAYS, List.of(question.getId()), null, "말머리1", 1);
        sectionRepository.save(section);

        Template template = new Template(List.of(section.getId()));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when
        TemplateResponse templateResponse = templateMapper.mapToTemplateResponse(reviewGroup, template);

        // then
        SectionResponse sectionResponse = templateResponse.sections().get(0);
        assertThat(sectionResponse.onSelectedOptionId()).isNull();
    }

    @Test
    void 가이드라인이_없는_경우_가이드_라인을_제공하지_않는다() {
        // given
        Question question = new Question(true, QuestionType.TEXT, "질문", null, 1);
        questionRepository.save(question);

        OptionGroup optionGroup = new OptionGroup(question.getId(), 1, 2);
        optionGroupRepository.save(optionGroup);

        OptionItem optionItem = new OptionItem("선택지", optionGroup.getId(), 1, OptionType.CATEGORY);
        optionItemRepository.save(optionItem);

        Section section = new Section(VisibleType.ALWAYS, List.of(question.getId()), null, "말머리1", 1);
        sectionRepository.save(section);

        Template template = new Template(List.of(section.getId()));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when
        TemplateResponse templateResponse = templateMapper.mapToTemplateResponse(reviewGroup, template);

        // then
        QuestionResponse questionResponse = templateResponse.sections().get(0).questions().get(0);
        assertAll(
                () -> assertThat(questionResponse.hasGuideline()).isFalse(),
                () -> assertThat(questionResponse.guideline()).isNull()
        );
    }

    @Test
    void 옵션_그룹이_없는_질문의_경우_옵션_그룹을_제공하지_않는다() {
        // given
        Question question = new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1);
        questionRepository.save(question);

        Section section = new Section(VisibleType.ALWAYS, List.of(question.getId()), null, "말머리1", 1);
        sectionRepository.save(section);

        Template template = new Template(List.of(section.getId()));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when
        TemplateResponse templateResponse = templateMapper.mapToTemplateResponse(reviewGroup, template);

        // then
        QuestionResponse questionResponse = templateResponse.sections().get(0).questions().get(0);
        assertThat(questionResponse.optionGroup()).isNull();
    }

    @Test
    void 템플릿_매핑_시_템플릿에_제공할_섹션이_없을_경우_예외가_발생한다() {
        // given
        Template template = new Template(List.of(1L));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when, then
        assertThatThrownBy(() -> templateMapper.mapToTemplateResponse(reviewGroup, template))
                .isInstanceOf(SectionNotFoundException.class);
    }

    @Test
    void 템플릿_매핑_시_옵션_그룹에_해당하는_옵션_아이템이_없을_경우_예외가_발생한다() {
        // given
        Question question1 = new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1);
        Question question2 = new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1);
        questionRepository.saveAll(List.of(question1, question2));

        OptionGroup optionGroup = new OptionGroup(question2.getId(), 1, 2);
        optionGroupRepository.save(optionGroup);

        Section section1 = new Section(VisibleType.ALWAYS, List.of(question1.getId()), null, "말머리1", 1);
        Section section2 = new Section(VisibleType.ALWAYS, List.of(question2.getId()), null, "말머리2", 2);
        sectionRepository.saveAll(List.of(section1, section2));

        Template template = new Template(List.of(section1.getId(), section2.getId()));
        templateRepository.save(template);

        ReviewGroup reviewGroup = new ReviewGroup("리뷰이명", "프로젝트명", "reviewRequestCode", "groupAccessCode");
        reviewGroupRepository.save(reviewGroup);

        // when, then
        assertThatThrownBy(() -> templateMapper.mapToTemplateResponse(reviewGroup, template))
                .isInstanceOf(MissingOptionItemsInOptionGroupException.class);
    }
}
