package reviewme.review.service.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.CheckBoxAnswerSelectedOptionRepository;
import reviewme.review.repository.CheckboxAnswerRepository;
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
    private final CheckBoxAnswerSelectedOptionRepository checkBoxAnswerSelectedOptionRepository;
    private final QuestionRepository questionRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        long templateId = review.getTemplateId();

        List<SectionAnswerResponse> sectionResponses = templateCacheRepository.findAllSectionByTemplateId(templateId)
                .stream()
                .map(section -> mapToSectionResponse(review, section))
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

    private SectionAnswerResponse mapToSectionResponse(Review review, Section section) {
        List<QuestionAnswerResponse> questionResponses = questionRepository
                .findByReviewAndSectionId(review.getId(), section.getId())
                .stream()
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
        OptionGroup optionGroup = templateCacheRepository.findOptionGroupByQuestionId(question.getId());

        List<OptionItemAnswerResponse> optionItemResponse = checkBoxAnswerSelectedOptionRepository
                .findSelectedOptionIdByReviewAndQuestion(review.getId(), question.getId())
                .stream()
                .map(templateCacheRepository::findOptionItemById)
                .map(optionItem -> new OptionItemAnswerResponse(
                        optionItem.getId(),
                        optionItem.getContent(),
                        true)
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
                question.getContent(),
                optionGroupAnswerResponse,
                null
        );
    }

    private QuestionAnswerResponse mapToTextQuestionResponse(Review review, Question question) {
        TextAnswer textAnswer = review.getTextAnswers()
                .stream()
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
