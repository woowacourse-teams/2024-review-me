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
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewCheckboxAnswer;
import reviewme.review.domain.abstraction.NewCheckboxAnswerSelectedOption;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.domain.abstraction.NewTextAnswer;
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

    public ReviewDetailResponse mapToReviewDetailResponse(NewReview review, ReviewGroup reviewGroup) {
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
            return mapToCheckboxQuestionResponse((NewCheckboxAnswer) answer, question);

        } else {
            return mapToTextQuestionResponse((NewTextAnswer) answer, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(NewCheckboxAnswer answer, Question question) {
        List<Long> selectedOptionIds = answer.getSelectedOptionIds()
                .stream()
                .map(NewCheckboxAnswerSelectedOption::getSelectedOptionId)
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

    private QuestionAnswerResponse mapToTextQuestionResponse(NewTextAnswer answer, Question question) {
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
