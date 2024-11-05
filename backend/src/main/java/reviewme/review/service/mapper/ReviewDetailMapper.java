package reviewme.review.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;

@Component
@RequiredArgsConstructor
public class ReviewDetailMapper {

    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        long templateId = review.getTemplateId();
        List<Section> sections = sectionRepository.findAllByTemplateId(templateId);
        List<SectionAnswerResponse> sectionResponses = sections.stream()
                .map(section -> mapToSectionResponse(review, section))
                .filter(sectionResponse -> !sectionResponse.questions().isEmpty())
                .toList();

        return new ReviewDetailResponse(
                templateId,
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                review.getCreatedDate(),
                sectionResponses
        );
    }

    private SectionAnswerResponse mapToSectionResponse(Review review, Section section) {
        List<QuestionAnswerResponse> questionResponses =
                questionRepository.findAllBySectionIdOrderByPosition(section.getId())
                        .stream()
                        .filter(question -> review.hasAnsweredQuestion(question.getId()))
                        .map(question -> mapToQuestionResponse(review, question))
                        .toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(Review review, Question question) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse(review, question);
        } else {
            return mapToTextQuestionResponse(review, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(Review review, Question question) {
        Set<Long> selectedOptionIds = review.getAnswersByType(CheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());

        List<OptionItemAnswerResponse> optionItemResponse = optionItemRepository.findByQuestionId(question.getId())
                .stream()
                .filter(optionItem -> selectedOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new OptionItemAnswerResponse(optionItem.getId(), optionItem.getContent(), true))
                .toList();

        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()));

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
                question.getContent(),
                optionGroupAnswerResponse,
                null
        );
    }

    private QuestionAnswerResponse mapToTextQuestionResponse(Review review,
                                                             Question question) {
        List<TextAnswer> textAnswers = review.getAnswersByType(TextAnswer.class);
        TextAnswer textAnswer = textAnswers.stream()
                .filter(answer -> answer.getQuestionId() == question.getId())
                .findFirst()
                .orElseThrow();

        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.getContent(),
                null,
                textAnswer.getContent()
        );
    }
}
