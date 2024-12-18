package reviewme.template.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.domain.Template;
import reviewme.template.domain.TemplateSection;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.exception.MissingOptionItemsInOptionGroupException;
import reviewme.template.service.exception.QuestionInSectionNotFoundException;
import reviewme.template.service.exception.SectionInTemplateNotFoundException;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Component
@RequiredArgsConstructor
public class TemplateMapper {

    public static final String REVIEWEE_NAME_PLACEHOLDER = "{revieweeName}";

    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public List<SectionResponse> mapSectionResponses(long templateId, long reviewGroupId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(reviewGroupId, templateId));

        return template.getSectionIds()
                .stream()
                .map(this::mapToSectionResponse)
                .toList();
    }

    private SectionResponse mapToSectionResponse(TemplateSection templateSection) {
        Section section = sectionRepository.findById(templateSection.getSectionId())
                .orElseThrow(() -> new SectionInTemplateNotFoundException(
                        templateSection.getTemplateId(), templateSection.getSectionId())
                );
        List<QuestionResponse> questionResponses = section.getQuestionIds()
                .stream()
                .map(this::mapToQuestionResponse)
                .toList();

        return new SectionResponse(
                section.getId(),
                section.getSectionName(),
                section.getVisibleType().name(),
                section.getOnSelectedOptionId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionResponse mapToQuestionResponse(SectionQuestion sectionQuestion) {
        Question question = questionRepository.findById(sectionQuestion.getQuestionId())
                .orElseThrow(() -> new QuestionInSectionNotFoundException(
                        sectionQuestion.getSectionId(), sectionQuestion.getQuestionId())
                );
        OptionGroupResponse optionGroupResponse = optionGroupRepository.findByQuestionId(question.getId())
                .map(this::mapToOptionGroupResponse)
                .orElse(null);

        return new QuestionResponse(
                question.getId(),
                question.isRequired(),
                question.getContent(),
                question.getQuestionType().name(),
                optionGroupResponse,
                question.hasGuideline(),
                question.getGuideline()
        );
    }

    private OptionGroupResponse mapToOptionGroupResponse(OptionGroup optionGroup) {
        List<OptionItem> optionItems = optionItemRepository.findAllByOptionGroupId(optionGroup.getId());
        if (optionItems.isEmpty()) {
            throw new MissingOptionItemsInOptionGroupException(optionGroup.getId());
        }

        List<OptionItemResponse> optionItemResponses = optionItems.stream()
                .map(this::mapToOptionItemResponse)
                .toList();

        return new OptionGroupResponse(
                optionGroup.getId(),
                optionGroup.getMinSelectionCount(),
                optionGroup.getMaxSelectionCount(),
                optionItemResponses
        );
    }

    private OptionItemResponse mapToOptionItemResponse(OptionItem optionItem) {
        return new OptionItemResponse(optionItem.getId(), optionItem.getContent());
    }
}
