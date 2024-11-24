package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.structured.StructuredTemplate;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundException;

@ServiceTest
class StructuredTemplateServiceTest {

    @Autowired
    private StructuredTemplateService structuredTemplateService;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Test
    void 템플릿_아이디를_통해_구조화된_템플릿을_응답한다() {
        // given
        Question requiredCheckboxQuestion = questionRepository.save(선택형_필수_질문());
        Question optionalCheckboxQuestion = questionRepository.save(선택형_옵션_질문());
        Question requiredTextQuestion = questionRepository.save(서술형_필수_질문());

        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(requiredCheckboxQuestion.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(optionalCheckboxQuestion.getId()));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
        OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(requiredCheckboxQuestion.getId())));
        Section section2 = sectionRepository.save(조건부로_보이는_섹션(
                List.of(requiredTextQuestion.getId(), optionalCheckboxQuestion.getId()), optionItem1.getId()));

        Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

        // when
        StructuredTemplate actual = structuredTemplateService.getStructuredTemplateById(template.getId());

        // then
        assertAll(
                () -> assertThat(actual.getTemplateId()).isEqualTo(template.getId()),
                () -> assertThat(actual.getSections().getSectionIds())
                        .containsExactlyInAnyOrder(section1.getId(), section2.getId()),
                () -> assertThat(actual.getQuestions().getQuestionIds())
                        .containsExactlyInAnyOrder(requiredCheckboxQuestion.getId(), optionalCheckboxQuestion.getId(),
                                requiredTextQuestion.getId()),
                () -> assertThat(actual.getOptionGroups().getOptionGroupIds())
                        .containsExactlyInAnyOrder(optionGroup1.getId(), optionGroup2.getId()),
                () -> assertThat(actual.getOptionItems().getOptionItemIds())
                        .containsExactlyInAnyOrder(optionItem1.getId(), optionItem2.getId(), optionItem3.getId(),
                                optionItem4.getId())
        );
    }

    @Test
    void 템플릿_아이디를_통해_체크박스_타입의_질문이_없는_구조화된_템플릿을_응답한다() {
        // given
        Question requiredQuestion = questionRepository.save(서술형_필수_질문());
        Question optionalQuestion = questionRepository.save(서술형_옵션_질문());

        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(requiredQuestion.getId())));
        Section section2 = sectionRepository.save(항상_보이는_섹션(
                List.of(requiredQuestion.getId(), optionalQuestion.getId())));

        Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

        // when
        StructuredTemplate actual = structuredTemplateService.getStructuredTemplateById(template.getId());

        // then
        assertAll(
                () -> assertThat(actual.getTemplateId()).isEqualTo(template.getId()),
                () -> assertThat(actual.getSections().getSectionIds())
                        .containsExactlyInAnyOrder(section1.getId(), section2.getId()),
                () -> assertThat(actual.getQuestions().getQuestionIds())
                        .containsExactlyInAnyOrder(requiredQuestion.getId(), optionalQuestion.getId())
        );
    }

    @Test
    void 템플릿_아이디에_해당하는_템플릿이_없을_경우_예외가_발생한다() {
        // given
        long wrongTemplateId = 1L;

        // when, then
        assertThatThrownBy(() -> structuredTemplateService.getStructuredTemplateById(wrongTemplateId))
                .isInstanceOf(TemplateNotFoundException.class);
    }
}
