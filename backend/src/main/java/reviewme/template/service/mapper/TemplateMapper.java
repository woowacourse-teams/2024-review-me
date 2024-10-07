package reviewme.template.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;

@Component
@RequiredArgsConstructor
public class TemplateMapper {

    private final TemplateCacheRepository templateCacheRepository;

    public TemplateResponse mapToTemplateResponse(ReviewGroup reviewGroup) {
        Template template = templateCacheRepository.findTemplateById(reviewGroup.getTemplateId());
        List<SectionResponse> sectionResponses = templateCacheRepository.findAllSectionByTemplateId(template.getId())
                .stream()
                .map(this::mapToSectionResponse)
                .toList();

        return new TemplateResponse(
                template.getId(),
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                sectionResponses
        );
    }

    private SectionResponse mapToSectionResponse(Section section) {
        List<QuestionResponse> questionResponses = templateCacheRepository.findAllQuestionBySectionId(section.getId())
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

    private QuestionResponse mapToQuestionResponse(Question question) {
        OptionGroupResponse optionGroupResponse = null;
        if (question.isSelectable()) {
            OptionGroup optionGroup = templateCacheRepository.findOptionGroupByQuestionId(question.getId());
            optionGroupResponse = mapToOptionGroupResponse(optionGroup);
        }

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
        List<OptionItem> optionItems = templateCacheRepository.findAllOptionItemByOptionGroupId(optionGroup.getId());
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
