package reviewme.template.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Component
@RequiredArgsConstructor
public class TemplateMapper {

    public static final String REVIEWEE_NAME_PLACEHOLDER = "{revieweeName}";

    private final TemplateRepository templateRepository;

    public List<SectionResponse> mapSectionResponses(long templateId, long reviewGroupId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(reviewGroupId, templateId));

        return template.getSections()
                .stream()
                .map(this::mapToSectionResponse)
                .toList();
    }

    private SectionResponse mapToSectionResponse(Section section) {
        List<QuestionResponse> questionResponses = section.getQuestions()
                .stream()
                .map(this::mapToQuestionResponse)
                .toList();

        return new SectionResponse(
                section.getId(),
                section.getSectionName(),
                section.getVisibleType(),
                section.getOnSelectedOptionId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionResponse mapToQuestionResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.isRequired(),
                question.getContent(),
                question.getQuestionType(),
                question.isSelectable() ? mapToOptionGroupResponse(question.getOptionGroup()) : null,
                question.hasGuideline(),
                question.getGuideline()
        );
    }

    private OptionGroupResponse mapToOptionGroupResponse(OptionGroup optionGroup) {
        List<OptionItemResponse> optionItemResponses = optionGroup.getOptionItems()
                .stream()
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
