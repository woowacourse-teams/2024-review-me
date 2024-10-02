package reviewme;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import reviewme.template.domain.SectionQuestion;
import reviewme.template.domain.TemplateSection;
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

    // TemplateId, list<Section>
    public Map<Long, List<Section>> sections = new HashMap<>();

    // sectionId, list<question>
    public Map<Long, List<Question>> questions = new HashMap<>();

    // questionId, list<optionGroup>
    public Map<Long, OptionGroup> optionGroups = new HashMap<>();

    // OptionGroupId, list<optionItem>
    public Map<Long, List<OptionItem>> optionItems = new HashMap<>();

    @PostConstruct
    public void init() {
        this.sections = loadSections();
        this.questions = loadQuestions();
        this.optionGroups = loadOptionGroups();
        this.optionItems = loadOptionItems();
    }

    private Map<Long, List<Section>> loadSections() {
        List<TemplateSection> templateSections = templateSectionRepository.findAll();
        Map<Long, List<Section>> sectionsByTemplate = new HashMap<>();
        // Map<templateId, List<Section>>
        for (TemplateSection templateSection : templateSections) {
            Section section = sectionRepository.findById(templateSection.getSectionId()).get();
            if (sectionsByTemplate.containsKey(templateSection.getTemplateId())) {
                List<Section> sectionGroup = sectionsByTemplate.get(templateSection.getTemplateId());
                sectionGroup.add(section);
            } else {
                sectionsByTemplate.put(templateSection.getTemplateId(), new ArrayList<>(List.of(section)));
            }
        }
        return sectionsByTemplate;
    }

    private Map<Long, List<Question>> loadQuestions() {
        List<Section> allSections = sectionRepository.findAll();
        Map<Long, List<Question>> questionsBySection = new HashMap<>();
        // Map<sectionId, List<question>>
        for (Section section : allSections) {
            List<Long> list = section.getQuestionIds()
                    .stream()
                    .map(SectionQuestion::getQuestionId)
                    .toList();
        List<Question> allQuestions = questionRepository.findAllById(list);
            questionsBySection.put(section.getId(), allQuestions);
        }
        return questionsBySection;
    }

    private Map<Long, OptionGroup> loadOptionGroups() {
        List<OptionGroup> allOptionGroups = optionGroupRepository.findAll();
        Map<Long, OptionGroup> optionGroupByQuestion = new HashMap<>();
        for (OptionGroup optionGroup : allOptionGroups) {
            optionGroupByQuestion.put(optionGroup.getQuestionId(), optionGroup);
        }
        return optionGroupByQuestion;
    }

    private Map<Long, List<OptionItem>> loadOptionItems() {
        List<OptionItem> allOptionItems = optionItemRepository.findAll();
        Map<Long, List<OptionItem>> optionItemsByOptionGroup = new HashMap<>();
        for (OptionItem optionItem : allOptionItems) {
            if (optionItemsByOptionGroup.containsKey(optionItem.getOptionGroupId())) {
                List<OptionItem> optionItemList = optionItemsByOptionGroup.get(optionItem.getOptionGroupId());
                optionItemList.add(optionItem);
                optionItemsByOptionGroup.put(optionItem.getOptionGroupId(), optionItemList);
            } else {
                optionItemsByOptionGroup.put(optionItem.getOptionGroupId(), new ArrayList<>(List.of(optionItem)));
            }
        }
        return optionItemsByOptionGroup;
    }

    public List<Section> getSectionsByTemplateId(long templateId) {
        return sections.get(templateId);
    }

    public List<Question> getQuestionsBySectionId(long sectionId) {
        return questions.get(sectionId);
    }


    public OptionGroup getOptionGroupByQuestionId(long questionId) {
        return optionGroups.get(questionId);
    }

    public List<OptionItem> getOptionItemsByOptionGroupId(long optionGroupId) {
        return optionItems.get(optionGroupId);
    }
}
