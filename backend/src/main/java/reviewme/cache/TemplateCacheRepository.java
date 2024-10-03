package reviewme.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;

@Component
@RequiredArgsConstructor
public class TemplateCacheRepository {

    private final TemplateCache templateCache;

    public Optional<Template> findTemplateById(long templateId) {
        return Optional.ofNullable(templateCache.getTemplates().get(templateId));
    }

    public Optional<Section> findSectionById(long sectionId) {
        return Optional.ofNullable(templateCache.getSections().get(sectionId));
    }

    public Optional<Question> findQuestionById(long questionId) {
        return Optional.ofNullable(templateCache.getQuestions().get(questionId));
    }

    public List<Section> findAllSectionByTemplateId(long templateId) {
        return templateCache.getTemplateSections().get(templateId);
        // TODO : null 검증 필요할까?
    }

    public List<Question> findAllQuestionByTemplateId(long templateId) {
        Map<Long, List<Section>> templateSections = templateCache.getTemplateSections();

        List<Section> sections = templateCache.getTemplateSections().get(templateId);
        // TODO : null 검증 필요할까?
        if (sections == null) {
            return Collections.emptyList();
        }
        return sections.stream()
                .flatMap(section -> templateCache.getSectionQuestions().get(section.getId()).stream())
                .toList();
    }
}
