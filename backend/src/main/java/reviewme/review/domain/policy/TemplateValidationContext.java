package reviewme.review.domain.policy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reviewme.review.domain.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.domain.exception.OptionItemNotFoundByOptionGroupIdException;
import reviewme.template.domain.exception.QuestionNotFoundException;

public class TemplateValidationContext {

    private final StructuredTemplate template;

    public TemplateValidationContext(StructuredTemplate template) {
        this.template = template;
    }

    public QuestionProperties getQuestionPropertiesById(long questionId) {
        Question question = template.getQuestions()
                .stream()
                .filter(q -> q.getId() == questionId)
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        return new QuestionProperties(question.getId(), question.isRequired());
    }

    public Set<Long> getQuestionIds() {
        return template.getQuestions()
                .stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> getRequiredQuestionIds() {
        return template.getQuestions()
                .stream()
                .filter(Question::isRequired)
                .map(Question::getId)
                .collect(Collectors.toSet());
    }

    public OptionGroupProperties getOptionGroupPropertiesByQuestionId(long questionId) {
        OptionGroup optionGroup = template.getOptionGroups()
                .stream()
                .filter(group -> group.getQuestionId() == questionId)
                .findFirst()
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(questionId));

        return new OptionGroupProperties(optionGroup.getId(),
                optionGroup.getMinSelectionCount(), optionGroup.getMaxSelectionCount());
    }

    public List<Long> getOptionItemIdsByOptionGroupId(long optionGroupId) {
        List<Long> optionItemsByOptionGroup = template.getOptionItems()
                .stream()
                .filter(optionItem -> optionItem.getOptionGroupId() == optionGroupId)
                .map(OptionItem::getId)
                .toList();

        if (optionItemsByOptionGroup.isEmpty()) {
            throw new OptionItemNotFoundByOptionGroupIdException(optionGroupId);
        }

        return optionItemsByOptionGroup;
    }

    @RequiredArgsConstructor
    @Getter
    public static class QuestionProperties {
        private final long id;
        private final boolean required;
    }

    @RequiredArgsConstructor
    @Getter
    public static class OptionGroupProperties {
        private final long id;
        private final int minSelectionCount;
        private final int maxSelectionCount;
    }
}
