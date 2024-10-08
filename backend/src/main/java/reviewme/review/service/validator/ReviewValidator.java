package reviewme.review.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewValidator {

    private final AnswerValidatorFactory answerValidatorFactory;
    private final TemplateCacheRepository templateCacheRepository;
    private final CheckboxAnswerRepository checkboxAnswerRepository;

    public void validate(Review review) {
        validateAnswer(review.getAnswers());
        validateAllAnswersContainedInTemplate(review);
        validateAllRequiredQuestionsAnswered(review);
    }

    private void validateAnswer(List<Answer> answers) {
        for (Answer answer : answers) {
            AnswerValidator validator = answerValidatorFactory.getAnswerValidator(answer.getClass());
            validator.validate(answer);
        }
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
        Set<Long> requiredQuestionIds = extractRequiredQuestionIds(review);
        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        reviewedQuestionIds.removeAll(reviewedQuestionIds);

        if (requiredQuestionIds.isEmpty()) {
            throw new MissingRequiredQuestionException(new ArrayList<>(requiredQuestionIds));
        }
    }

    private Set<Long> extractRequiredQuestionIds(Review review) {
        return review.getAnswers()
                .stream()
                .map(answer -> templateCacheRepository.findQuestionById(answer.getQuestionId()))
                .filter(Question::isRequired)
                .map(Question::getId)
                .collect(Collectors.toSet());
    }
}
