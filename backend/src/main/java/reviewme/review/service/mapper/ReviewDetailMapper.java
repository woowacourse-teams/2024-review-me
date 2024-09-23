package reviewme.review.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
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
        List<Question> questions = questionRepository.findAllByTemplatedId(templateId);
        List<Long> questionIds = questions.stream()
                .map(Question::getId)
                .toList();
        Map<Long, OptionGroup> optionGroupsByQuestion = optionGroupRepository.findAllByQuestionIds(questionIds)
                .stream()
                .collect(Collectors.toMap(OptionGroup::getQuestionId, Function.identity()));
        Map<Long, List<OptionItem>> optionItemsByOptionGroup = optionItemRepository.findAllByQuestionIds(questionIds)
                .stream()
                .collect(Collectors.groupingBy(OptionItem::getOptionGroupId));

        List<SectionAnswerResponse> sectionResponses = sections.stream()
                .map(section -> mapToSectionResponse(review, reviewGroup, section, questions,
                        optionGroupsByQuestion, optionItemsByOptionGroup))
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

    private SectionAnswerResponse mapToSectionResponse(Review review, ReviewGroup reviewGroup, Section section,
                                                       List<Question> questions,
                                                       Map<Long, OptionGroup> optionGroupsByQuestion,
                                                       Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        List<QuestionAnswerResponse> questionResponses = questions.stream()
                .filter(question -> review.hasAnsweredQuestion(question.getId()))
                .map(question -> mapToQuestionResponse(review, reviewGroup, question,
                        optionGroupsByQuestion, optionItemsByOptionGroup))
                .toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.convertHeader(REVIEWEE_NAME_PLACEHOLDER, reviewGroup.getReviewee()),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(Review review, ReviewGroup reviewGroup, Question question,
                                                         Map<Long, OptionGroup> optionGroupsByQuestion,
                                                         Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse(review, reviewGroup, question,
                    optionGroupsByQuestion, optionItemsByOptionGroup);
        } else {
            return mapToTextQuestionResponse(review, reviewGroup, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(Review review, ReviewGroup reviewGroup,
                                                                 Question question,
                                                                 Map<Long, OptionGroup> optionGroupsByQuestion,
                                                                 Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        OptionGroup optionGroup = optionGroupsByQuestion.get(question.getId());
        List<OptionItem> optionItems = optionItemsByOptionGroup.get(optionGroup.getId());
        Set<Long> selectedOptionIds = review.getAllCheckBoxOptionIds();

        List<OptionItemAnswerResponse> optionItemResponse = optionItems.stream()
                .map(optionItem -> new OptionItemAnswerResponse(
                        optionItem.getId(),
                        optionItem.getContent(),
                        selectedOptionIds.contains(optionItem.getId()))
                )
                .toList();

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
        List<TextAnswer> textAnswers = review.getTextAnswers();
        TextAnswer textAnswer = textAnswers.stream()
                .filter(answer -> answer.getQuestionId() == question.getId())
                .findFirst()
                .orElseThrow();

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
