package reviewme.template.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.domain.exception.NoOptionItemsInOptionGroupException;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.Question2Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.dto.response.OptionGroupResponse;
import reviewme.template.dto.response.OptionItemResponse;
import reviewme.template.dto.response.QuestionResponse;
import reviewme.template.dto.response.SectionResponse;
import reviewme.template.dto.response.TemplateResponse;
import reviewme.template.repository.SectionRepository;

@Component
@RequiredArgsConstructor
public class TemplateMapper {

    private final SectionRepository sectionRepository;
    private final Question2Repository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public TemplateResponse mapToTemplateResponse(ReviewGroup reviewGroup, Template template) {
        List<SectionResponse> sectionResponses = template.getSectionIds()
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

    private SectionResponse mapToSectionResponse(long sectionId) {
        Section section = sectionRepository.getSectionById(sectionId);
        List<QuestionResponse> questionResponses = section.getQuestionIds()
                .stream()
                .map(this::mapToQuestionResponse)
                .toList();

        return new SectionResponse(
                sectionId,
                section.getVisibleType().name(),
                section.getOnSelectedOptionId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionResponse mapToQuestionResponse(long questionId) {
        Question2 question = questionRepository.getQuestionById(questionId);
        OptionGroup optionGroup = optionGroupRepository.getOptionGroupByQuestionId(questionId);
        OptionGroupResponse optionGroupResponse = mapToOptionGroupResponse(optionGroup);

        return new QuestionResponse(
                question.getId(),
                question.isRequired(),
                question.getContent(),
                question.getContent(),
                optionGroupResponse,
                question.hasGuideline(),
                question.getGuideline()
        );
    }

    private OptionGroupResponse mapToOptionGroupResponse(OptionGroup optionGroup) {
        List<OptionItem> optionItems = optionItemRepository.findAllByOptionGroupId(optionGroup.getId());
        if (optionItems.isEmpty()) {
            throw new NoOptionItemsInOptionGroupException(optionGroup.getId());
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
