package reviewme.template.domain.structured;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public class StructuredTemplateCreator {

    public StructuredTemplate create(Template template, List<Section> sectionList, List<Question> questionList,
                                     List<OptionGroup> optionGroupList, List<OptionItem> optionItemList) {
        Sections sections = new Sections(sectionList);
        Questions questions = new Questions(questionList);
        OptionGroups optionGroups = new OptionGroups(optionGroupList);
        OptionItems optionItems = new OptionItems(optionItemList);

        validateTemplate(template);
        validateSections(template, sections);
        validateQuestions(sections, questions);
        validateOptionGroup(questions, optionGroups);
        validateOptionItems(optionGroups, optionItems);
        validateSectionsVisibility(sections, optionItems);

        return new StructuredTemplate(template, sections, questions, optionGroups, optionItems);
    }

    public StructuredTemplate createWithoutCheckBox(Template template, List<Section> sectionList, List<Question> questionList) {
        Sections sections = new Sections(sectionList);
        Questions questions = new Questions(questionList);

        validateTemplate(template);
        validateSections(template, sections);
        validateSectionsVisibility(sections);
        validateQuestions(sections, questions);
        validateNoCheckboxQuestion(questions);

        return new StructuredTemplate(template, sections, questions);
    }

    private void validateTemplate(Template template) {
        if (template == null) {
            throw new StructuredTemplateNotExistTemplateException();
        }
    }

    private void validateSections(Template template, Sections sections) {
        Set<Long> sectionIdsOfTemplate = new HashSet<>(template.getSectionIds());
        Set<Long> sectionIds = new HashSet<>(sections.getSectionIds());
        if (!sectionIdsOfTemplate.equals(sectionIds)) {
            throw new StructuredTemplateSectionValidationException(template.getId(), sectionIds);
        }
    }

    private void validateQuestions(Sections sections, Questions questions) {
        Set<Long> questionIdsOfSections = sections.getQuestionIds();
        Set<Long> questionIds = new HashSet<>(questions.getQuestionIds());
        if (!questionIdsOfSections.equals(questionIds)) {
            throw new StructuredTemplateQuestionValidationException(questionIdsOfSections, questionIds);
        }
    }

    private void validateOptionGroup(Questions questions, OptionGroups optionGroups) {
        Set<Long> checkboxQuestionIds = new HashSet<>(questions.getCheckboxQuestionIds());
        Set<Long> questionIdsOfOptionGroup = optionGroups.getQuestionIds();
        if (!checkboxQuestionIds.equals(questionIdsOfOptionGroup) || checkboxQuestionIds.size() < optionGroups.size()) {
            throw new StructuredTemplateOptionGroupValidationException(checkboxQuestionIds,
                    questionIdsOfOptionGroup);
        }
    }

    private void validateOptionItems(OptionGroups optionGroups, OptionItems optionItems) {
        Set<Long> optionGroupIds = new HashSet<>(optionGroups.getOptionGroupIds());
        Set<Long> OptionGroupIdsOfItems = optionItems.getOptionGroupIds();
        if (!optionGroupIds.equals(OptionGroupIdsOfItems)) {
            throw new StructuredTemplateOptionItemsValidationException(optionGroupIds, OptionGroupIdsOfItems);
        }
    }

    private void validateSectionsVisibility(Sections sections, OptionItems optionItems) {
        List<Long> optionItemIds = optionItems.getOptionItemIds();
        List<Long> invisibleSectionIds = sections.getInvisibleSectionIds(optionItemIds);
        if (!invisibleSectionIds.isEmpty()) {
            throw new StructuredTemplateInvisibleSectionFoundException(invisibleSectionIds, optionItemIds);
        }
    }

    private void validateSectionsVisibility(Sections sections) {
        List<Long> invisibleSectionIds = sections.getInvisibleSectionIds();
        if (!invisibleSectionIds.isEmpty()) {
            throw new StructuredTemplateInvisibleSectionFoundException(invisibleSectionIds);
        }
    }

    private void validateNoCheckboxQuestion(Questions questions) {
        if (questions.hasCheckboxQuestion()) {
            throw new StructuredTemplateCheckBoxQuestionFoundException(questions.getQuestionIds());
        }
    }
}
