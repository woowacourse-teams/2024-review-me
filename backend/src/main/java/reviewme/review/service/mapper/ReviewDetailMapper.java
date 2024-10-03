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
    private final CheckboxAnswerRepository checkboxAnswerRepository;
    private final CheckBoxAnswerSelectedOptionRepository checkBoxAnswerSelectedOptionRepository;

    public ReviewDetailResponse mapToReviewDetailResponse(Review review, ReviewGroup reviewGroup) {
        long templateId = review.getTemplateId();
        Map<Long, List<OptionItem>> optionItemsByQuestion = mapToOptionItemsByQuestion(review);
        Map<Section, List<Question>> questionsBySection = mapToQuestionsBySection(review);

        List<SectionAnswerResponse> sectionResponses = questionsBySection.keySet()
                .stream()
                .map(section -> mapToSectionResponse(
                        section, questionsBySection.get(section), review.getTextAnswers(), optionItemsByQuestion
                ))
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

    private Map<Long, List<OptionItem>> mapToOptionItemsByQuestion(Review review) {
        // 1. 리뷰가 갖고있는 checkboxAnswer들을 가져옵니다.
        List<CheckboxAnswer> checkboxAnswers = checkboxAnswerRepository.findAllByReviewId(review.getId());

        // 2. checkboxAnswer들을 같은 question id인 것끼리 묶어서 Map으로 만듭니다.
        Map<Long, Long> checkboxAnswersByQuestionId = checkboxAnswers.stream()
                .collect(Collectors.toMap(CheckboxAnswer::getQuestionId, CheckboxAnswer::getId));

        // 3. checkboxAnswer들의 id를 모아서, 해당 id로 selectedOptions들을 조회합니다.
        Map<Long, List<Long>> selectedOptionsByCheckBoxAnswerId = getSelectedOptionsByCheckBoxAnswerId(checkboxAnswers);

        // 4. 각 리뷰의 CheckboxAnswer에 해당하는 OptionItem들을 Map에 넣습니다.
        return checkboxAnswersByQuestionId.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        checkboxAnswerByQuestionId -> mapOptionItemsForCheckboxAnswers(
                                checkboxAnswerByQuestionId.getValue(), selectedOptionsByCheckBoxAnswerId
                        )
                ));
    }

    private Map<Section, List<Question>> mapToQuestionsBySection(Review review) {
        return Stream.concat(
                        review.getCheckboxAnswers().stream().map(CheckboxAnswer::getQuestionId),
                        review.getTextAnswers().stream().map(TextAnswer::getQuestionId)
                )
                .map(templateCacheRepository::findQuestionById)
                .collect(Collectors.groupingBy(
                        templateCacheRepository::findSectionByQuestion, TreeMap::new, Collectors.toList()
                ));
    }

    private SectionAnswerResponse mapToSectionResponse(Section section, List<Question> questions,
                                                       List<TextAnswer> textAnswers,
                                                       Map<Long, List<OptionItem>> optionItemsByQuestion) {
        List<QuestionAnswerResponse> questionResponses = questions.stream()
                .map(question -> mapToQuestionResponse(question, textAnswers,
                        optionItemsByQuestion.get(question.getId())))
                .toList();

        return new SectionAnswerResponse(
                section.getId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionAnswerResponse mapToQuestionResponse(Question question,
                                                         List<TextAnswer> textAnswers,
                                                         List<OptionItem> optionItems) {
        if (question.isSelectable()) {
            return mapToCheckboxQuestionResponse(question, optionItems);
        } else {
            TextAnswer textAnswer = textAnswers.stream()
                    .filter(textAnswer1 -> textAnswer1.getQuestionId() == question.getId())
                    .findFirst()
                    .orElseThrow();

            return mapToTextQuestionResponse(question, textAnswer);
        }
    }

    private QuestionAnswerResponse mapToCheckboxQuestionResponse(Question question, List<OptionItem> optionItems) {
        OptionGroup optionGroup = templateCacheRepository.findOptionGroupByQuestionId(question.getId());

        List<OptionItemAnswerResponse> optionItemResponse = optionItems.stream()
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

    private QuestionAnswerResponse mapToTextQuestionResponse(Question question, TextAnswer textAnswer) {
        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.getContent(),
                null,
                textAnswer.getContent()
        );
    }

    private Map<Long, List<Long>> getSelectedOptionsByCheckBoxAnswerId(List<CheckboxAnswer> checkboxAnswers) {
        List<Long> checkboxAnswerIds = checkboxAnswers.stream()
                .map(CheckboxAnswer::getId)
                .toList();

        List<CheckBoxAnswerSelectedOption> checkBoxAnswerSelectedOptionGroup = checkBoxAnswerSelectedOptionRepository
                .findAllByCheckboxAnswerIds(checkboxAnswerIds);

        Map<Long, List<Long>> checkBoxAnswerSelectedOptions = new HashMap<>();
        for (CheckBoxAnswerSelectedOption option : checkBoxAnswerSelectedOptionGroup) {
            checkBoxAnswerSelectedOptions
                    .computeIfAbsent(option.getCheckboxAnswerId(), selectedOptionIds -> new ArrayList<>())
                    .add(option.getSelectedOptionId());
        }
        return checkBoxAnswerSelectedOptions;
    }

    private List<OptionItem> mapOptionItemsForCheckboxAnswers(long checkBoxAnswerId,
                                                              Map<Long, List<Long>> selectedOptionsByCheckBoxAnswerId) {
        return selectedOptionsByCheckBoxAnswerId.get(checkBoxAnswerId)
                .stream()
                .map(templateCacheRepository::findOptionItem)
                .toList();
    }
}
