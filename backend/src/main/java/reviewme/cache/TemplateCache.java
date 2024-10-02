package reviewme.cache;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.repository.TemplateSectionRepository;

@Component
@RequiredArgsConstructor
@Getter
public class TemplateCache {

    private final TemplateRepository templateRepository;
    private final TemplateSectionRepository templateSectionRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    private Map<Long, Template> templates = new HashMap<>();
    private Map<Long, Section> sections = new HashMap<>();
    private Map<Long, Question> questions = new HashMap<>();
    private Map<Long, OptionGroup> optionGroups = new HashMap<>();
    private Map<Long, OptionItem> optionItems = new HashMap<>();

    @PostConstruct
    public void init() {
        this.templates = loadTemplates();
        this.sections = loadSections();
        this.questions = loadQuestions();
        this.optionGroups = loadOptionGroups();
        this.optionItems = loadOptionItems();

    }

    private Map<Long, Template> loadTemplates() {
        return templateRepository.findAll()
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
}
