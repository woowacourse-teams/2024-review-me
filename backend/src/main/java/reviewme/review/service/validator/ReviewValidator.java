package reviewme.review.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.template.domain.Section;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.repository.SectionRepository;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewValidator {

    private final AnswerValidatorFactory answerValidatorFactory;

    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;

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
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(review.getTemplateId());
        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        if (!providedQuestionIds.containsAll(reviewedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(reviewedQuestionIds, providedQuestionIds);
        }
    }

    private void validateAllRequiredQuestionsAnswered(Review review) {
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

    private Set<Long> extractDisplayedQuestionIds(Review review) {
        Set<Long> selectedOptionIds = review.getAnswersByType(CheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());
        List<Section> sections = sectionRepository.findAllByTemplateId(review.getTemplateId());

        return sections.stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionIds))
                .flatMap(section -> section.getQuestionIds().stream())
                .map(SectionQuestion::getQuestionId)
                .collect(Collectors.toSet());
    }
}
