package reviewme.review.service.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.template.domain.Section;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final QuestionRepository questionRepository;

    public void validate(Review review) {
        validateAllAnswersContainedInTemplate(review);
        validateAllRequiredQuestionsAnswered(review);
    }

    private void validateAllAnswersContainedInTemplate(Review review) {
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(review.getTemplateId());
        Set<Long> reviewedQuestionIds = review.getAllQuestionIdsFromAnswers();
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

        Set<Long> reviewedQuestionIds = review.getAllQuestionIdsFromAnswers();
        if (!reviewedQuestionIds.containsAll(requiredQuestionIds)) {
            List<Long> missingRequiredQuestionIds = new ArrayList<>(requiredQuestionIds);
            missingRequiredQuestionIds.removeAll(reviewedQuestionIds);
            throw new MissingRequiredQuestionException(missingRequiredQuestionIds);
        }
    }

    private Set<Long> extractDisplayedQuestionIds(Review review) {
        Set<Long> selectedOptionIds = review.getAllCheckBoxOptionIds();
        List<Section> sections = sectionRepository.findAllByTemplateId(review.getTemplateId());

        return sections.stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionIds))
                .flatMap(section -> section.getQuestionIds().stream())
                .map(SectionQuestion::getQuestionId)
                .collect(Collectors.toSet());
    }
}