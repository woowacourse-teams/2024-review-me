package reviewme.review.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;

@Component
@RequiredArgsConstructor
public class ReviewDetailMapper {

    private final TemplateCacheRepository templateCacheRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        long templateId = review.getTemplateId();
        Map<Long, Answer> questionAnswers = review.getAnswers()
                .stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Function.identity()));

        List<SectionAnswerResponse> sectionResponses = templateCacheRepository.findAllSectionByTemplateId(templateId)
                .stream()
                .map(section -> mapToSectionResponse(questionAnswers, section))
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

    private SectionAnswerResponse mapToSectionResponse(Map<Long, Answer> questionAnswers, Section section) {
        List<QuestionAnswerResponse> questionResponses = templateCacheRepository.findAllQuestionBySectionId(
                        section.getId())
                .stream()
                .filter(question -> questionAnswers.containsKey(question.getId()))
                .map(question -> mapToQuestionResponse(questionAnswers.get(question.getId()), question))
                .toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(Answer answer, Question question) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse((CheckboxAnswer) answer, question);

        } else {
            return mapToTextQuestionResponse((TextAnswer) answer, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(CheckboxAnswer answer, Question question) {
        List<Long> selectedOptionIds = answer.getSelectedOptionIds()
                .stream()
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .toList();

        OptionGroup optionGroup = templateCacheRepository.findOptionGroupByQuestionId(question.getId());
        OptionGroupAnswerResponse optionGroupAnswerResponse = mapToOptionGroupResponse(optionGroup, selectedOptionIds);

        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.getContent(),
                optionGroupAnswerResponse,
                null
        );
    }

    private OptionGroupAnswerResponse mapToOptionGroupResponse(OptionGroup optionGroup, List<Long> selectedOptionIds) {
        List<OptionItemAnswerResponse> optionItemResponse = templateCacheRepository
                .findAllOptionItemByOptionGroupId(optionGroup.getId())
                .stream()
                .filter(optionItem -> selectedOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new OptionItemAnswerResponse(
                        optionItem.getId(),
                        optionItem.getContent(),
                        true))
                .toList();

        return new OptionGroupAnswerResponse(
                optionGroup.getId(),
                optionGroup.getMinSelectionCount(),
                optionGroup.getMaxSelectionCount(),
                optionItemResponse
        );
    }

    private QuestionAnswerResponse mapToTextQuestionResponse(TextAnswer answer, Question question) {
        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.getContent(),
                null,
                answer.getContent()
        );
    }
}
