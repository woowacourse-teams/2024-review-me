package reviewme.review.service.module;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.TextAnswers;
import reviewme.review.service.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;

@Component
@RequiredArgsConstructor
public class ReviewDetailMapper {

    public static final String REVIEWEE_NAME_PLACEHOLDER = "{revieweeName}";

    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        long templateId = review.getTemplateId();
        List<Section> sections = sectionRepository.findAllByTemplateId(templateId);
        List<SectionAnswerResponse> sectionResponses = sections.stream()
                .map(section -> mapToSectionResponse(review, reviewGroup, section))
                .filter(SectionAnswerResponse::hasAnsweredQuestion)
                .toList();

        return new ReviewDetailResponse(
                templateId,
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                review.getCreatedDate(),
                sectionResponses
        );
    }

    private SectionAnswerResponse mapToSectionResponse(Review review, ReviewGroup reviewGroup, Section section) {
        List<QuestionAnswerResponse> questionResponses = questionRepository.findAllBySectionId(section.getId())
                .stream()
                .filter(question -> review.hasAnsweredQuestion(question.getId()))
                .map(question -> mapToQuestionResponse(review, reviewGroup, question))
                .toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.convertHeader(REVIEWEE_NAME_PLACEHOLDER, reviewGroup.getReviewee()),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(Review review, ReviewGroup reviewGroup, Question question) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse(review, reviewGroup, question);
        } else {
            return mapToTextQuestionResponse(review, reviewGroup, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(Review review, ReviewGroup reviewGroup,
                                                                 Question question) {
        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()));
        Set<Long> selectedOptionItemIds = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());

        List<OptionItemAnswerResponse> optionItemResponse =
                optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(review.getId(), question.getId())
                        .stream()
                        .map(optionItem -> new OptionItemAnswerResponse(
                                optionItem.getId(),
                                optionItem.getContent(),
                                selectedOptionItemIds.contains(optionItem.getId()))
                        ).toList();

        OptionGroupAnswerResponse optionGroupAnswerResponse = new OptionGroupAnswerResponse(
                optionGroup.getId(),
                optionGroup.getMinSelectionCount(),
                optionGroup.getMaxSelectionCount(),
                optionItemResponse
        );

        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.convertContent(REVIEWEE_NAME_PLACEHOLDER, reviewGroup.getReviewee()),
                optionGroupAnswerResponse,
                null
        );
    }

    private QuestionAnswerResponse mapToTextQuestionResponse(Review review, ReviewGroup reviewGroup,
                                                             Question question) {
        TextAnswers textAnswers = new TextAnswers(review.getTextAnswers());
        TextAnswer textAnswer = textAnswers.getAnswerByQuestionId(question.getId());
        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.convertContent(REVIEWEE_NAME_PLACEHOLDER, reviewGroup.getReviewee()),
                null,
                textAnswer.getContent()
        );
    }
}
