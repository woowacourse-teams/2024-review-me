package reviewme.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reviewme.cache.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateJpaRepository;

@Component
@RequiredArgsConstructor
@Getter
public class TemplateCache {

    private final TemplateJpaRepository templateJpaRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    private Map<Long, Template> templates = new HashMap<>();
    private Map<Long, Section> sections = new HashMap<>();
    private Map<Long, Question> questions = new HashMap<>();
    private Map<Long, OptionGroup> optionGroups = new HashMap<>();
    private Map<Long, OptionItem> optionItems = new HashMap<>();

    private Map<Long, List<Section>> templateSections = new HashMap<>();
    private Map<Long, List<Question>> sectionQuestions = new HashMap<>();
    private Map<Long, OptionGroup> questionOptionGroups = new HashMap<>();
    private Map<Long, List<OptionItem>> optionGroupOptionItems = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.templates = loadTemplates();
        this.sections = loadSections();
        this.questions = loadQuestions();
        this.optionGroups = loadOptionGroups();
        this.optionItems = loadOptionItems();
        this.templateSections = loadTemplateSections();
        this.sectionQuestions = loadSectionQuestions();
        this.questionOptionGroups = loadQuestionOptionGroups();
        this.optionGroupOptionItems = loadOptionGroupOptionItems();
    }

    private Map<Long, Template> loadTemplates() {
        return templateJpaRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Template::getId, Function.identity()));
    }

    private Map<Long, Section> loadSections() {
        return sectionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Section::getId, Function.identity()));
    }

    private Map<Long, Question> loadQuestions() {
        return questionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
    }

    private Map<Long, OptionGroup> loadOptionGroups() {
        return optionGroupRepository.findAll()
                .stream()
                .collect(Collectors.toMap(OptionGroup::getId, Function.identity()));
    }

    private Map<Long, OptionItem> loadOptionItems() {
        return optionItemRepository.findAll()
                .stream()
                .collect(Collectors.toMap(OptionItem::getId, Function.identity()));
    }

    private Map<Long, List<Section>> loadTemplateSections() {
        return templates.keySet()
                .stream()
                .collect(Collectors.toMap(templateId -> templateId, sectionRepository::findAllByTemplateId));
    }

    private Map<Long, List<Question>> loadSectionQuestions() {
        return sections.keySet()
                .stream()
                .collect(Collectors.toMap(sectionId -> sectionId, questionRepository::findAllBySectionId));
    }

    private Map<Long, OptionGroup> loadQuestionOptionGroups() {
        return questions.values()
                .stream()
                .filter(Question::isSelectable)
                .collect(Collectors.toMap(
                        Question::getId,
                        question -> optionGroupRepository.findByQuestionId(question.getId())
                                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()))
                ));
    }

    private Map<Long, List<OptionItem>> loadOptionGroupOptionItems() {
        return optionGroups.keySet()
                .stream()
                .collect(Collectors.toMap(optionGroupId -> optionGroupId, optionItemRepository::findAllByOptionGroupId));
    }
}
