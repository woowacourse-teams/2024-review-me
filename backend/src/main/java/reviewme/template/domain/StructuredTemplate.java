package reviewme.template.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import reviewme.template.domain.exception.StructuredTemplateSectionValidationException;
import reviewme.template.service.exception.StructuredTemplateOptionGroupValidationException;
import reviewme.template.service.exception.StructuredTemplateOptionItemsValidationException;
import reviewme.template.service.exception.StructuredTemplateQuestionValidationException;
import reviewme.template.service.exception.StructuredTemplateSectionQuestionValidationException;

public class StructuredTemplate {

    private final Template template;
    private final List<Section> sections;
    private final List<Question> questions;
    private final List<OptionGroup> optionGroups;
    private final List<OptionItem> optionItems;

    public StructuredTemplate(Template template, List<Section> sections, List<Question> questions,
                              List<OptionGroup> optionGroups, List<OptionItem> optionItems) {
        validateSections(template, sections);
        validateSectionQuestions(sections);
        validateQuestions(sections, questions);
        validateOptionGroup(questions, optionGroups);
        validateOptionItems(optionGroups, optionItems);
        this.template = template;
        this.sections = sections;
        this.questions = questions;
        this.optionGroups = optionGroups;
        this.optionItems = optionItems;
    }

    private void validateSections(Template template, List<Section> sections) {
        List<Long> templateSectionIds = template.getSectionIds();
        List<Long> sectionIds = sections.stream()
                .map(Section::getId)
                .toList();

        if (!new HashSet<>(templateSectionIds).containsAll(sectionIds) || templateSectionIds.size() != sectionIds.size()) {
            throw new StructuredTemplateSectionValidationException(template.getId(), sectionIds);
        }
    }

    private void validateSectionQuestions(List<Section> sections) {
        List<Long> sectionQuestionIds = sections.stream()
                .flatMap(section -> section.getQuestionIds().stream())
                .toList();

        int originalSize = sectionQuestionIds.size();
        Set<Long> deduplicatedQuestionIds = new HashSet<>(sectionQuestionIds);
        int deduplicatedSize = deduplicatedQuestionIds.size();

        if (originalSize != deduplicatedSize) {
            throw new StructuredTemplateSectionQuestionValidationException(sectionQuestionIds);
        }
    }

    private void validateQuestions(List<Section> sections, List<Question> questions) {
        List<Long> sectionQuestionIds = sections.stream()
                .flatMap(section -> section.getQuestionIds().stream())
                .toList();

        List<Long> questionIds = questions.stream()
                .map(Question::getId)
                .toList();

        if (!new HashSet<>(sectionQuestionIds).containsAll(questions) || sectionQuestionIds.size() != questionIds.size()) {
            throw new StructuredTemplateQuestionValidationException(sectionQuestionIds, questionIds);
        }
    }

    private void validateOptionGroup(List<Question> questions, List<OptionGroup> optionGroups) {
        List<Long> checkboxQuestionIds = questions.stream()
                .filter(Question::isSelectable)
                .map(Question::getId)
                .toList();

        List<Long> optionGroupQuestionIds = optionGroups.stream()
                .map(OptionGroup::getId)
                .toList();

        if (!new HashSet<>(checkboxQuestionIds).containsAll(optionGroupQuestionIds)
            || checkboxQuestionIds.size() != optionGroupQuestionIds.size()) {
            throw new StructuredTemplateOptionGroupValidationException(checkboxQuestionIds, optionGroupQuestionIds);
        }
    }

    private void validateOptionItems(List<OptionGroup> optionGroups, List<OptionItem> optionItems) {
        List<Long> optionGroupIds = optionGroups.stream()
                .map(OptionGroup::getId)
                .toList();

        Set<Long> itemGroupIds = optionItems.stream()
                .map(OptionItem::getOptionGroupId)
                .collect(Collectors.toSet());

        if (!itemGroupIds.containsAll(optionGroupIds) || optionGroupIds.size() != itemGroupIds.size()) {
            throw new StructuredTemplateOptionItemsValidationException(optionGroupIds, itemGroupIds);
        }
    }
}
