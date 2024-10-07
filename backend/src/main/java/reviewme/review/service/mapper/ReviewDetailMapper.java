package reviewme.review.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.CheckBoxAnswerSelectedOptionRepository;
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

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        System.out.println("start2=============================");

        long templateId = review.getTemplateId();

        //  추상화 적용 후, answer.questionId 로 변경예정
        List<Long> reviewQuestionIds = Stream.concat(
                review.getTextAnswers()
                        .stream()
                        .map(TextAnswer::getQuestionId),
                review.getCheckboxAnswers()
                        .stream()
                        .map(CheckboxAnswer::getQuestionId)
        ).toList();

        List<SectionAnswerResponse> sectionResponses = templateCacheRepository.findAllSectionByTemplateId(templateId)
                .stream()
                .map(section -> mapToSectionResponse(review, section, reviewQuestionIds))
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

    private SectionAnswerResponse mapToSectionResponse(Review review, Section section, List<Long> reviewQuestionIds ) {
        List<QuestionAnswerResponse> questionResponses = templateCacheRepository.findAllQuestionBySectionId(section.getId())
                .stream()
                .filter(question -> reviewQuestionIds.contains(question.getId()))
                .map(question -> mapToQuestionResponse(review, question))
                .toList();

//        List<QuestionAnswerResponse> questionResponses = questionRepository
//                .findByReviewIdAndSectionId(review.getId(), section.getId())
//                .stream()
//                .map(question -> mapToQuestionResponse(review, question))
//                .toList();

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
        List<OptionItem> optionItems = templateCacheRepository.findAllOptionItemByOptionGroupId(optionGroup.getId());
        Set<Long> selectedOptionIds = review.getAllCheckBoxOptionIds();

        List<OptionItemAnswerResponse> optionItemResponse = optionItems.stream()
                .filter(optionItem -> selectedOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new OptionItemAnswerResponse(
                        optionItem.getId(),
                        optionItem.getContent(),
                        true))
                .toList();

//        List<OptionItemAnswerResponse> optionItemResponse = checkBoxAnswerSelectedOptionRepository
//                .findSelectedOptionIdByReviewAndQuestion(review.getId(), question.getId())
//                .stream()
//                .map(templateCacheRepository::findOptionItemById)
//                .map(optionItem -> new OptionItemAnswerResponse(
//                        optionItem.getId(),
//                        optionItem.getContent(),
//                        true)
//                ).toList();

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
                .get();

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
