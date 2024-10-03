package reviewme.cache;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.exception.OptionGroupNotFoundException;
import reviewme.cache.exception.QuestionNotFoundException;
import reviewme.cache.exception.SectionNotFoundException;
import reviewme.cache.exception.TemplateNotFoundException;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;

@Component
@RequiredArgsConstructor
public class TemplateCacheRepository {

    private final TemplateCache templateCache;

    public Template findTemplateById(long templateId) {
        return Optional.ofNullable(templateCache.getTemplates().get(templateId))
                .orElseThrow(() -> new TemplateNotFoundException(templateId));
    }

    public Section findSectionById(long sectionId) {
        return Optional.ofNullable(templateCache.getSections().get(sectionId))
                .orElseThrow(() -> new SectionNotFoundException(sectionId));
    }

    public Question findQuestionById(long questionId) {
        return Optional.ofNullable(templateCache.getQuestions().get(questionId))
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    public OptionGroup findOptionGroupById(long optionGroupId) {
        return Optional.ofNullable(templateCache.getOptionGroups().get(optionGroupId))
                .orElseThrow(() -> new OptionGroupNotFoundException(optionGroupId));
    }

    public List<Section> findAllSectionByTemplateId(long templateId) {
        Template template = findTemplateById(templateId);
        return templateCache.getTemplateSections().get(template.getId());
    }

    public List<Question> findAllQuestionByTemplateId(long templateId) {
        List<Section> sections = findAllSectionByTemplateId(templateId);
        return sections.stream()
                .flatMap(section -> templateCache.getSectionQuestions().get(section.getId()).stream())
                .toList();
    }

    public List<Question> findAllQuestionBySectionId(long sectionId) {
        Section section = findSectionById(sectionId);
        return templateCache.getSectionQuestions().get(section.getId());
    }

    public OptionGroup findOptionGroupByQuestionId(long questionId) {
        Question question = findQuestionById(questionId);
        return templateCache.getQuestionOptionGroups().get(question.getId());
    }

    public List<OptionItem> findAllOptionItemByOptionGroupId(long optionGroupId) {
        OptionGroup optionGroup = findOptionGroupById(optionGroupId);
        return templateCache.getOptionGroupOptionItems().get(optionGroup.getId());
    }
}
