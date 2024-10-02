package reviewme.template.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.TemplateCache;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.OptionGroupResponse;
import reviewme.template.service.dto.response.OptionItemResponse;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Component
@RequiredArgsConstructor
public class TemplateMapper_cacheVer {

    public static final String REVIEWEE_NAME_PLACEHOLDER = "{revieweeName}";

    private final TemplateRepository templateRepository;
    private final TemplateCache templateCache;

    public TemplateResponse mapToTemplateResponse(ReviewGroup reviewGroup) {
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId()
                ));

        List<Section> sections = templateCache.getSectionsByTemplateId(template.getId());
        List<SectionResponse> sectionResponses = sections.stream()
                .map(this::mapToSectionResponse)
                .toList();

        return new TemplateResponse(
                1L,
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                sectionResponses
        );
    }

    private SectionResponse mapToSectionResponse(Section section) {
        List<Question> questions = templateCache.getQuestionsBySectionId(section.getId());

        List<QuestionResponse> questionResponses = questions.stream()
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
        OptionGroup optionGroup = templateCache.getOptionGroupByQuestionId(question.getId());
        OptionGroupResponse optionGroupResponse = mapToOptionGroupResponse(optionGroup);

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
        List<OptionItem> optionItems = templateCache.getOptionItemsByOptionGroupId(optionGroup.getId());

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
