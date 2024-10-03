package reviewme.review.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.template.domain.Section;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final TextAnswerValidator textAnswerValidator;
    private final CheckBoxAnswerValidator checkBoxAnswerValidator;
    private final TemplateCacheRepository templateCacheRepository;

    public void validate(Review review) {
        validateAnswer(review.getTextAnswers(), review.getCheckboxAnswers());
        validateAllAnswersContainedInTemplate(review);
        validateAllRequiredQuestionsAnswered(review);
    }

    private void validateAnswer(List<TextAnswer> textAnswers, List<CheckboxAnswer> checkboxAnswers) {
        textAnswers.forEach(textAnswerValidator::validate);
        checkboxAnswers.forEach(checkBoxAnswerValidator::validate);
    }

    private void validateAllAnswersContainedInTemplate(Review review) {
        Set<Long> providedQuestionIds = templateCacheRepository.findAllQuestionByTemplateId(review.getTemplateId())
                .stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        if (!providedQuestionIds.containsAll(reviewedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(reviewedQuestionIds, providedQuestionIds);
        }
    }

    private void validateAllRequiredQuestionsAnswered(Review review) {
        Set<Long> requiredQuestionIds = extractDisplayedQuestions(review).stream()
                .filter(Question::isRequired)
                .map(Question::getId)
                .collect(Collectors.toSet());

        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        reviewedQuestionIds.removeAll(reviewedQuestionIds);

        if (requiredQuestionIds.isEmpty()) {
            throw new MissingRequiredQuestionException(new ArrayList<>(requiredQuestionIds));
        }
    }

    private Set<Question> extractDisplayedQuestions(Review review) {
        List<Section> visibleSections = templateCacheRepository.findAllSectionByTemplateId(review.getTemplateId())
                .stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(review.getAllCheckBoxOptionIds()))
                .toList();

        return visibleSections.stream()
                .flatMap(section -> templateCacheRepository.findAllQuestionBySectionId(section.getId()).stream())
                .collect(Collectors.toSet());
    }
}
