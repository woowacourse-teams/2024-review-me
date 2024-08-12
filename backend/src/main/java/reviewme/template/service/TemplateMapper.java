package reviewme.template.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.domain.exception.MissingOptionItemsInOptionGroupException;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.Question2Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.domain.Template;
import reviewme.template.domain.TemplateSection;
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
                .map(templateSection -> mapToSectionResponse(templateSection, reviewGroup))
                .toList();

        return new TemplateResponse(
                template.getId(),
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                sectionResponses
        );
    }

    private SectionResponse mapToSectionResponse(TemplateSection templateSection, ReviewGroup reviewGroup) {
        Section section = sectionRepository.getSectionById(templateSection.getSectionId());
        List<QuestionResponse> questionResponses = section.getQuestionIds()
                .stream()
                .map(sectionQuestion -> mapToQuestionResponse(sectionQuestion, reviewGroup))
                .toList();

        return new SectionResponse(
                section.getId(),
                section.getVisibleType().name(),
                section.getOnSelectedOptionId(),
                section.convertHeader("{revieweeName}", reviewGroup.getReviewee()),
                questionResponses
        );
    }

    private QuestionResponse mapToQuestionResponse(SectionQuestion sectionQuestion, ReviewGroup reviewGroup) {
        Question2 question = questionRepository.getQuestionById(sectionQuestion.getQuestionId());
        OptionGroupResponse optionGroupResponse = optionGroupRepository.findByQuestionId(question.getId())
                .map(this::mapToOptionGroupResponse)
                .orElse(null);

        return new QuestionResponse(
                question.getId(),
                question.isRequired(),
                question.convertContent("{revieweeName}", reviewGroup.getReviewee()),
                question.getQuestionType().name(),
                optionGroupResponse,
                question.hasGuideline(),
                question.convertGuideLine("{revieweeName}", reviewGroup.getReviewee())
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
