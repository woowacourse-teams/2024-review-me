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
import reviewme.review.domain.NewCheckboxAnswer;
import reviewme.review.domain.NewCheckboxAnswerSelectedOption;
import reviewme.review.domain.NewReview;
import reviewme.review.domain.NewTextAnswer;
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

    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(NewReview review, ReviewGroup reviewGroup) {
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
                .map(section -> mapToSectionResponse(review, section, questions,
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

    private SectionAnswerResponse mapToSectionResponse(NewReview review, Section section,
                                                       List<Question> questions,
                                                       Map<Long, OptionGroup> optionGroupsByQuestion,
                                                       Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        List<QuestionAnswerResponse> questionResponses = questions.stream()
                .filter(question -> section.containsQuestionId(question.getId()))
                .filter(question -> review.hasAnsweredQuestion(question.getId()))
                .map(question -> mapToQuestionResponse(
                        review, question, optionGroupsByQuestion, optionItemsByOptionGroup)
                ).toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(NewReview review, Question question,
                                                         Map<Long, OptionGroup> optionGroupsByQuestion,
                                                         Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse(review, question, optionGroupsByQuestion, optionItemsByOptionGroup);
        } else {
            return mapToTextQuestionResponse(review, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(NewReview review,
                                                                 Question question,
                                                                 Map<Long, OptionGroup> optionGroupsByQuestion,
                                                                 Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        OptionGroup optionGroup = optionGroupsByQuestion.get(question.getId());
        List<OptionItem> optionItems = optionItemsByOptionGroup.get(optionGroup.getId());
        Set<Long> selectedOptionIds = review.getAnswersByType(NewCheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(NewCheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());

        List<OptionItemAnswerResponse> optionItemResponse = optionItems.stream()
                .filter(optionItem -> selectedOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new OptionItemAnswerResponse(optionItem.getId(), optionItem.getContent(), true))
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
                question.getContent(),
                optionGroupAnswerResponse,
                null
        );
    }

    private QuestionAnswerResponse mapToTextQuestionResponse(NewReview review,
                                                             Question question) {
        List<NewTextAnswer> textAnswers = review.getAnswersByType(NewTextAnswer.class);
        NewTextAnswer textAnswer = textAnswers.stream()
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
