package reviewme.template.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.domain.Template;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundException;

@Component
@RequiredArgsConstructor
public class StructuredTemplateService {

    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    @Transactional
    public StructuredTemplate getStructuredTemplateById(long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException(templateId));

        List<Section> sections = sectionRepository.findAllByTemplateId(template.getId());
        List<Question> questions = questionRepository.findAllByTemplatedId(template.getId());
        List<Long> questionIds = questions.stream()
                .map(Question::getId)
                .toList();
        List<OptionGroup> optionGroups = optionGroupRepository.findAllByQuestionIds(questionIds);
        List<OptionItem> optionItems = optionItemRepository.findAllByQuestionIds(questionIds);

        if (optionGroups.isEmpty() && optionItems.isEmpty()) {
            return new StructuredTemplate(template, sections, questions);
        }
        return new StructuredTemplate(template, sections, questions, optionGroups, optionItems);
    }
}

