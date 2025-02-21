package reviewme.review.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.SelectionRange;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Component
@RequiredArgsConstructor
public class ReviewDetailMapper {

    private final TemplateRepository templateRepository;

    /*
        TODO:
         조회 전용 로직을 만드는 게 좋겠다, Template + 리뷰 정보를 한 번에 내려줘야 한다.
         Template에서 정보를 가져오는 건 쉽다 (연관관계 있음), 리뷰 관련 정보를 가져와서 어떻게 섞을지 고민하자.
    */
    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(reviewGroup.getId(),
                        reviewGroup.getTemplateId()));

        List<Section> sections = template.getSections();
        List<Question> questions = sections.stream()
                .flatMap(section -> section.getQuestions().stream())
                .toList();
        Map<Long, OptionGroup> optionGroupsByQuestion = questions.stream()
                .filter(Question::isCheckbox)
                .collect(Collectors.toMap(Question::getId, Question::getOptionGroup));
        Map<Long, List<OptionItem>> optionItemsByOptionGroup = optionGroupsByQuestion.values().stream()
                .collect(Collectors.toMap(OptionGroup::getId, OptionGroup::getOptionItems));

        List<SectionAnswerResponse> sectionResponses = sections.stream()
                .map(section -> mapToSectionResponse(review, section, questions,
                        optionGroupsByQuestion, optionItemsByOptionGroup))
                .filter(sectionResponse -> !sectionResponse.questions().isEmpty())
                .toList();

        return new ReviewDetailResponse(
                template.getId(),
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                review.getCreatedDate(),
                sectionResponses
        );
    }

    private SectionAnswerResponse mapToSectionResponse(Review review, Section section,
                                                       List<Question> questions,
                                                       Map<Long, OptionGroup> optionGroupsByQuestion,
                                                       Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        List<QuestionAnswerResponse> questionResponses = questions.stream()
                .filter(section::contains)
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

    private QuestionAnswerResponse mapToQuestionResponse(Review review, Question question,
                                                         Map<Long, OptionGroup> optionGroupsByQuestion,
                                                         Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        if (question.isCheckbox()) {
            return mapToCheckboxQuestionResponse(review, question, optionGroupsByQuestion, optionItemsByOptionGroup);
        } else {
            return mapToTextQuestionResponse(review, question);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(Review review,
                                                                 Question question,
                                                                 Map<Long, OptionGroup> optionGroupsByQuestion,
                                                                 Map<Long, List<OptionItem>> optionItemsByOptionGroup) {
        OptionGroup optionGroup = optionGroupsByQuestion.get(question.getId());
        List<OptionItem> optionItems = optionItemsByOptionGroup.get(optionGroup.getId());
        Set<Long> selectedOptionIds = review.getAnswersByType(CheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());

        List<OptionItemAnswerResponse> optionItemResponse = optionItems.stream()
                .filter(optionItem -> selectedOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new OptionItemAnswerResponse(optionItem.getId(), optionItem.getContent(), true))
                .toList();
        SelectionRange selectionRange = optionGroup.getSelectionRange();

        OptionGroupAnswerResponse optionGroupAnswerResponse = new OptionGroupAnswerResponse(
                optionGroup.getId(),
                selectionRange.getMinSelectionCount(),
                selectionRange.getMaxSelectionCount(),
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
