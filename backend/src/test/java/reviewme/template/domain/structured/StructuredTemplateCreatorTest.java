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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.exception.StructuredTemplateCheckBoxQuestionFoundException;
import reviewme.template.domain.exception.StructuredTemplateInvisibleSectionFoundException;
import reviewme.template.domain.exception.StructuredTemplateNotExistTemplateException;
import reviewme.template.domain.exception.StructuredTemplateOptionGroupValidationException;
import reviewme.template.domain.exception.StructuredTemplateOptionItemsValidationException;
import reviewme.template.domain.exception.StructuredTemplateQuestionValidationException;
import reviewme.template.domain.exception.StructuredTemplateSectionValidationException;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class StructuredTemplateCreatorTest {

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

    @Nested
    @DisplayName("일반적인 구조화된 템플릿 생성 테스트")
    class DefaultStructuredTemplateCreate {

        @Test
        void 모든_타입의_질문이_포함된_구조화된_템플릿을_생성한다() {
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

            Section section1 = sectionRepository.save(
                    항상_보이는_섹션(List.of(requiredCheckboxQuestion.getId(), optionalTextQuestion.getId())));
            Section section2 = sectionRepository.save(
                    조건부로_보이는_섹션(List.of(optionalCheckboxQuestion.getId(), requiredTextQuestion.getId()),
                            optionItem1.getId()));
            Section section3 = sectionRepository.save(
                    조건부로_보이는_섹션(List.of(optionalCheckboxQuestion.getId(), requiredTextQuestion.getId()),
                            optionItem2.getId()));

            Template template = templateRepository.save(템플릿(
                    List.of(section1.getId(), section2.getId(), section3.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertDoesNotThrow(() -> templateCreator.create(
                    template,
                    List.of(section1, section2, section3),
                    List.of(requiredCheckboxQuestion, optionalCheckboxQuestion, requiredTextQuestion,
                            optionalTextQuestion),
                    List.of(optionGroup1, optionGroup2),
                    List.of(optionItem1, optionItem2, optionItem3, optionItem4)
            ));
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

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    null,
                    List.of(section1, section2),
                    List.of(checkboxQuestion, textQuestion),
                    List.of(optionGroup),
                    List.of(optionItem1, optionItem2))
            ).isInstanceOf(StructuredTemplateNotExistTemplateException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_템플릿과_섹션의_관계가_일치하지_않으면_예외가_발생한다() {
            // given
            Question checkboxQuestion = questionRepository.save(선택형_필수_질문());
            Question textQuestion = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(checkboxQuestion.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1),
                    List.of(checkboxQuestion, textQuestion),
                    List.of(optionGroup),
                    List.of(optionItem1, optionItem2))
            ).isInstanceOf(StructuredTemplateSectionValidationException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_섹션과_질문의_관계가_일치하지_않으면_예외가_발생한다() {
            // given
            Question checkboxQuestion = questionRepository.save(선택형_필수_질문());
            Question textQuestion = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(checkboxQuestion.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1, section2),
                    List.of(checkboxQuestion),
                    List.of(optionGroup),
                    List.of(optionItem1, optionItem2))
            ).isInstanceOf(StructuredTemplateQuestionValidationException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_질문과_옵션그룹의_관계가_일치하지_않으면_예외가_발생한다_하나의_질문에_두개_이상의_그룹이_묶인_경우() {
            // given
            Question checkboxQuestion = questionRepository.save(선택형_필수_질문());
            Question textQuestion = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(checkboxQuestion.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

            OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(checkboxQuestion.getId()));
            OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
            OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1, section2),
                    List.of(checkboxQuestion, textQuestion),
                    List.of(optionGroup1, optionGroup2),
                    List.of(optionItem1, optionItem2, optionItem3, optionItem4))
            ).isInstanceOf(StructuredTemplateOptionGroupValidationException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_질문과_옵션그룹의_관계가_일치하지_않으면_예외가_발생한다_질문과_관계없는_그룹이_있는_경우() {
            // given
            Question checkboxQuestion1 = questionRepository.save(선택형_필수_질문());
            Question checkboxQuestion2 = questionRepository.save(선택형_필수_질문());
            Question textQuestion = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(checkboxQuestion1.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

            OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(checkboxQuestion2.getId()));
            OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
            OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion1.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1, section2),
                    List.of(checkboxQuestion1, textQuestion),
                    List.of(optionGroup1, optionGroup2),
                    List.of(optionItem1, optionItem2, optionItem3, optionItem4))
            ).isInstanceOf(StructuredTemplateOptionGroupValidationException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_옵션그룹과_옵션항목의_관계가_일치하지_않으면_예외가_발생한다() {
            // given
            Question checkboxQuestion1 = questionRepository.save(선택형_필수_질문());
            Question checkboxQuestion2 = questionRepository.save(선택형_필수_질문());
            Question textQuestion = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(checkboxQuestion1.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

            OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(checkboxQuestion2.getId()));
            OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
            OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion1.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(textQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1, section2),
                    List.of(checkboxQuestion1, textQuestion),
                    List.of(optionGroup1),
                    List.of(optionItem1, optionItem2, optionItem3, optionItem4))
            ).isInstanceOf(StructuredTemplateOptionItemsValidationException.class);
        }

        @Test
        void 구조화된_템플릿_생성시_노출될_수_없는_섹션이_있으면_예외가_발생한다() {
            // given
            Question checkboxQuestion1 = questionRepository.save(선택형_필수_질문());
            Question checkboxQuestion2 = questionRepository.save(선택형_필수_질문());
            Question textQuestion1 = questionRepository.save(서술형_필수_질문());
            Question textQuestion2 = questionRepository.save(서술형_필수_질문());

            OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(checkboxQuestion1.getId()));
            OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
            OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

            OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(checkboxQuestion2.getId()));
            OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));

            Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(checkboxQuestion1.getId())));
            Section section2 = sectionRepository.save(
                    조건부로_보이는_섹션(List.of(textQuestion1.getId()), optionItem1.getId()));
            Section section3 = sectionRepository.save(
                    조건부로_보이는_섹션(List.of(textQuestion2.getId()), optionItem3.getId()));

            Template template = templateRepository.save(
                    템플릿(List.of(section1.getId(), section2.getId(), section3.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.create(
                    template,
                    List.of(section1, section2, section3),
                    List.of(checkboxQuestion1, textQuestion1, textQuestion2),
                    List.of(optionGroup1),
                    List.of(optionItem1, optionItem2))
            ).isInstanceOf(StructuredTemplateInvisibleSectionFoundException.class);
        }
    }

    @Nested
    @DisplayName("체크박스 타입의 질문이 없는 구조화된 템플릿 생성 테스트")
    class NoCheckboxStructuredTemplateCreate {

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿을_생성한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion = questionRepository.save(서술형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertDoesNotThrow(() -> templateCreator.createWithoutCheckBox(
                    template,
                    List.of(section1, section2),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion)
            ));
        }

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿_생성시_템플릿이_존재하지_않으면_예외가_발생한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion = questionRepository.save(서술형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.createWithoutCheckBox(
                    null,
                    List.of(section1, section2),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion)
            )).isInstanceOf(StructuredTemplateNotExistTemplateException.class);
        }

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿_생성시_템플릿과_섹션의_관계가_일치하지_않으면_예외가_발생한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion = questionRepository.save(서술형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.createWithoutCheckBox(
                    template,
                    List.of(section1, section2),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion)
            )).isInstanceOf(StructuredTemplateSectionValidationException.class);
        }

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿_생성시_섹션과_질문의_관계가_일치하지_않으면_예외가_발생한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion1 = questionRepository.save(서술형_옵션_질문());
            Question optionalQuestion2 = questionRepository.save(서술형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion1.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion1.getId())));

            Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.createWithoutCheckBox(
                    template,
                    List.of(section1, section2),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion1, optionalQuestion2)
            )).isInstanceOf(StructuredTemplateQuestionValidationException.class);
        }

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿_생성시_노출될_수_없는_섹션이_있으면_예외가_발생한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion1 = questionRepository.save(서술형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion1.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion1.getId())));
            Section section3 = sectionRepository.save(조건부로_보이는_섹션(
                    List.of(requiredQuestion1.getId()), 1L));

            Template template = templateRepository.save(템플릿(
                    List.of(section1.getId(), section2.getId(), section3.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.createWithoutCheckBox(
                    template,
                    List.of(section1, section2, section3),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion1)
            )).isInstanceOf(StructuredTemplateInvisibleSectionFoundException.class);
        }

        @Test
        void 체크박스_타입의_질문이_제외된_구조화된_템플릿_생성시_체크박스_타입의_질문이_존재하면_예외가_발생한다() {
            // given
            Question requiredQuestion1 = questionRepository.save(서술형_필수_질문());
            Question requiredQuestion2 = questionRepository.save(서술형_필수_질문());
            Question optionalQuestion = questionRepository.save(서술형_옵션_질문());
            Question checkboxQuestion = questionRepository.save(선택형_옵션_질문());

            Section section1 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion1.getId(), optionalQuestion.getId())));
            Section section2 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), optionalQuestion.getId())));
            Section section3 = sectionRepository.save(항상_보이는_섹션(
                    List.of(requiredQuestion2.getId(), checkboxQuestion.getId())));

            Template template = templateRepository.save(템플릿(
                    List.of(section1.getId(), section2.getId(), section3.getId())));

            StructuredTemplateCreator templateCreator = new StructuredTemplateCreator();

            // when, then
            assertThatThrownBy(() -> templateCreator.createWithoutCheckBox(
                    template,
                    List.of(section1, section2, section3),
                    List.of(requiredQuestion1, requiredQuestion2, optionalQuestion, checkboxQuestion)
            )).isInstanceOf(StructuredTemplateCheckBoxQuestionFoundException.class);
        }
    }
}
