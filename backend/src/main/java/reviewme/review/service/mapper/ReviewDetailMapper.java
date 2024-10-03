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

    private Map<Section, List<Question>> mapToQuestionsBySection(Review review) {
        Map<Section, List<Question>> questionsBySection = Stream.concat(
                        review.getCheckboxAnswers().stream().map(CheckboxAnswer::getQuestionId),
                        review.getTextAnswers().stream().map(TextAnswer::getQuestionId)
                )
                .map(templateCacheRepository::findQuestionById)
                .collect(Collectors.groupingBy(templateCacheRepository::findSectionByQuestion));

        // TreeMap을 사용하여 Section의 getId()를 기준으로 정렬
        Map<Section, List<Question>> sortedQuestionsBySection = new TreeMap<>(Comparator.comparing(Section::getId));
        sortedQuestionsBySection.putAll(questionsBySection);

        return sortedQuestionsBySection;
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

    private Map<Long, List<OptionItem>> mapToOptionItemsByQuestion(Review review) {
        // 리뷰가 갖고있는 checkboxAnswer들을 가져온다.
        List<CheckboxAnswer> checkboxAnswers = checkboxAnswerRepository.findAllByReviewId(review.getId());
        // checkboxAnswer들을 같은 question id인 것끼리 묶어 map으로 만든다.
        // Map <question id, checkboxanswer>
        Map<Long, Long> checkboxAnswersByQuestionId = checkboxAnswers.stream()
                .collect(Collectors.toMap(CheckboxAnswer::getQuestionId, CheckboxAnswer::getId));
        // checkboxAnswer들의 id들을 모은다.
        List<Long> checkboxAnswerIds = checkboxAnswers.stream()
                .map(CheckboxAnswer::getId)
                .toList();
        // checkboxAnswer의 id들을 갖고 checkBoxAnswerSelectedOption들을 조회해,
        List<CheckBoxAnswerSelectedOption> checkBoxAnswerSelectedOptionGroup = checkBoxAnswerSelectedOptionRepository
                .findAllByCheckboxAnswerIds(checkboxAnswerIds)
                .stream()
                .toList();
        // checkboxAnswerid, selectedOptionid 조합으로 map을 만든다.
        // Map<checkboxAnswerId, List<selectedOptionId>>
        Map<Long, List<Long>> checkBoxAnswerSelectedOptions = new HashMap<>();
        for (CheckBoxAnswerSelectedOption checkBoxAnswerSelectedOption : checkBoxAnswerSelectedOptionGroup) {
            if (checkBoxAnswerSelectedOptions.containsKey(checkBoxAnswerSelectedOption.getCheckboxAnswerId())) {
                List<Long> selectedOptionIds = checkBoxAnswerSelectedOptions.get(  // TODO: 예외처리 필요
                        checkBoxAnswerSelectedOption.getCheckboxAnswerId());
                selectedOptionIds.add(checkBoxAnswerSelectedOption.getSelectedOptionId());
            } else {
                checkBoxAnswerSelectedOptions.put(checkBoxAnswerSelectedOption.getCheckboxAnswerId(),
                        new ArrayList<>(List.of(checkBoxAnswerSelectedOption.getSelectedOptionId())));
            }
        }

        // checkboxAnswer를 하나씩 꺼내서 갖고있는 seletecOptionId를 list에 넣는다. -> cache 로 해결
        // Map <questionId, list<optionId>>
        Map<Long, List<OptionItem>> optionItemsByQuestion = new HashMap<>();
        for (Entry<Long, Long> checkboxAnswerByQuestionEntry : checkboxAnswersByQuestionId.entrySet()) {
            List<OptionItem> optionItemGroup = new ArrayList<>();
            List<Long> optionIds = checkBoxAnswerSelectedOptions.get(checkboxAnswerByQuestionEntry.getValue());
            for (long optionId : optionIds) {
                optionItemGroup.add(templateCacheRepository.findAllOptionItems(optionId));  // TODO: 예외처리 필요
            }
            optionItemsByQuestion.put(checkboxAnswerByQuestionEntry.getKey(), optionItemGroup);
        }
        return optionItemsByQuestion;
    }
}
