package reviewme.template.domain.structured;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundException;

@ServiceTest
class StructuredTemplateFinderTest {

    @Autowired
    private StructuredTemplateFinder templateCreator;

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
    void 체크박스_타입의_질문이_포함된_구조화된_템플릿을_찾는다() {
        // given
        Question requiredCheckboxQuestion = questionRepository.save(선택형_필수_질문());
        Question optionalCheckboxQuestion = questionRepository.save(선택형_옵션_질문());
        Question requiredTextQuestion = questionRepository.save(서술형_필수_질문());
        Question optionalTextQuestion = questionRepository.save(서술형_옵션_질문());

        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(requiredCheckboxQuestion.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(optionalCheckboxQuestion.getId()));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
        OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

        Section section1 = sectionRepository.save(항상_보이는_섹션(
                List.of(requiredCheckboxQuestion.getId(), optionalTextQuestion.getId())));
        Section section2 = sectionRepository.save(조건부로_보이는_섹션(
                List.of(optionalCheckboxQuestion.getId(), requiredTextQuestion.getId()), optionItem1.getId()));
        Section section3 = sectionRepository.save(조건부로_보이는_섹션(
                List.of(optionalCheckboxQuestion.getId(), requiredTextQuestion.getId()), optionItem2.getId()));

        Template template = templateRepository.save(템플릿(
                List.of(section1.getId(), section2.getId(), section3.getId())));

        // when, then
        assertDoesNotThrow(() -> templateCreator.find(template.getId()));
    }

    @Test
    void 체크박스_타입의_질문이_없는_구조화된_템플릿을_찾는다() {
        // given
        Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
        Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
        Question optionalQuestion = questionRepository.save(서술형_옵션_질문());

        Section section1 = sectionRepository.save(항상_보이는_섹션(
                List.of(requiredQuestion1.getId(), optionalQuestion.getId())));
        Section section2 = sectionRepository.save(항상_보이는_섹션(
                List.of(requiredQuestion2.getId(), optionalQuestion.getId())));

        Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

        // when, then
        assertDoesNotThrow(() -> templateCreator.find(template.getId()));
    }

    @Test
    void 구조화된_템플릿_생성시_템플릿이_존재하지_않으면_예외가_발생한다() {
        // given
        Question checkboxQuestion = questionRepository.save(선택형_필수_질문());
        Question textQuestion = questionRepository.save(서술형_필수_질문());

        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(checkboxQuestion.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion.getId())));
        Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

        long noExistsTemplateId = 1L;

        // when, then
        assertThatThrownBy(() -> templateCreator.find(noExistsTemplateId))
                .isInstanceOf(TemplateNotFoundException.class);
    }
}
