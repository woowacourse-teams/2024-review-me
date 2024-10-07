package reviewme.review.service.abstraction.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewCheckboxAnswerSelectedOption;
import reviewme.review.domain.abstraction.NewCheckboxAnswer;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.template.domain.Section;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.repository.SectionRepository;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NewReviewValidator {

    private final AnswerValidatorFactory answerValidatorFactory;

    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;

    public void validate(NewReview review) {
        validateAnswer(review.getAnswers());
        validateAllAnswersContainedInTemplate(review);
        validateAllRequiredQuestionsAnswered(review);
    }

    private void validateAnswer(List<Answer> answers) {
        for (Answer answer : answers) {
            NewAnswerValidator validator = answerValidatorFactory.getAnswerValidator(answer.getClass());
            validator.validate(answer);
        }
    }

    private void validateAllAnswersContainedInTemplate(NewReview review) {
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(review.getTemplateId());
        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        if (!providedQuestionIds.containsAll(reviewedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(reviewedQuestionIds, providedQuestionIds);
        }
    }

    private void validateAllRequiredQuestionsAnswered(NewReview review) {
        Set<Long> displayedQuestionIds = extractDisplayedQuestionIds(review);
        Set<Long> requiredQuestionIds = questionRepository.findAllById(displayedQuestionIds)
                .stream()
                .filter(Question::isRequired)
                .map(Question::getId)
                .collect(Collectors.toSet());

        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        if (!reviewedQuestionIds.containsAll(requiredQuestionIds)) {
            List<Long> missingRequiredQuestionIds = new ArrayList<>(requiredQuestionIds);
            missingRequiredQuestionIds.removeAll(reviewedQuestionIds);
            throw new MissingRequiredQuestionException(missingRequiredQuestionIds);
        }
    }

    private Set<Long> extractDisplayedQuestionIds(NewReview review) {
        Set<Long> selectedOptionIds = review.getAnswersByType(NewCheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(NewCheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());
        List<Section> sections = sectionRepository.findAllByTemplateId(review.getTemplateId());

        return sections.stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionIds))
                .flatMap(section -> section.getQuestionIds().stream())
                .map(SectionQuestion::getQuestionId)
                .collect(Collectors.toSet());
    }
}
