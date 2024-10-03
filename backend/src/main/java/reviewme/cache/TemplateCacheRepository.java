package reviewme.cache;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.exception.QuestionNotFoundException;
import reviewme.cache.exception.SectionNotFoundException;
import reviewme.cache.exception.TemplateNotFoundException;
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
}
