
package reviewme.highlight.service.validator;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.exception.SubmittedAnswerAndProvidedAnswerMismatchException;
import reviewme.review.repository.AnswerRepository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
@RequiredArgsConstructor
public class HighlightValidator {

    private final AnswerRepository answerRepository;

    public void validate(HighlightsRequest request, ReviewGroup reviewGroup) {
        validateQuestionContainsAnswer(request);
        validateReviewGroupContainsAnswer(request, reviewGroup);
    }

    private void validateQuestionContainsAnswer(HighlightsRequest request) {
        Set<Long> providedAnswerIds = answerRepository.findIdsByQuestionId(request.questionId());
        List<Long> submittedAnswerIds = request.getUniqueAnswerIds();

        if (!providedAnswerIds.containsAll(submittedAnswerIds)) {
            throw new SubmittedAnswerAndProvidedAnswerMismatchException(providedAnswerIds, submittedAnswerIds);
        }
    }

    private void validateReviewGroupContainsAnswer(HighlightsRequest request, ReviewGroup reviewGroup) {
        Set<Long> providedAnswerIds = answerRepository.findIdsByReviewGroupId(reviewGroup.getId());
        List<Long> submittedAnswerIds = request.getUniqueAnswerIds();

        if (!providedAnswerIds.containsAll(submittedAnswerIds)) {
            throw new SubmittedAnswerAndProvidedAnswerMismatchException(providedAnswerIds, submittedAnswerIds);
        }
    }
}
