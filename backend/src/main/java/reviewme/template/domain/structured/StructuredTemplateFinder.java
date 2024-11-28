package reviewme.template.domain.structured;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.template.domain.Template;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundException;

@Component
@RequiredArgsConstructor
public class StructuredTemplateFinder {

    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public StructuredTemplate find(long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException(templateId));

        Sections sections = new Sections(sectionRepository.findAllByTemplateId(template.getId()));
        Questions questions = new Questions(questionRepository.findAllByTemplatedId(template.getId()));

        if (questions.hasCheckboxQuestion()) {
            List<Long> questionIds = questions.getQuestionIds();
            OptionGroups optionGroups = new OptionGroups(optionGroupRepository.findAllByQuestionIds(questionIds));
            OptionItems optionItems = new OptionItems(optionItemRepository.findAllByQuestionIds(questionIds));

            return new StructuredTemplate(template, sections, questions, optionGroups, optionItems);
        }

        return new StructuredTemplate(template, sections, questions);
    }
}
