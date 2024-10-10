package reviewme.highlight.service.validator;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.exception.SubmittedAnswerAndProvidedAnswerMismatchException;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;

@Component
@RequiredArgsConstructor
public class HighlightValidator {

    private final AnswerRepository answerRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final QuestionRepository questionRepository;

    public void validate(HighlightsRequest request, long reviewGroupId) {
        validateAnswerByReviewGroup(request, reviewGroupId);
        validateQuestionByReviewGroup(request, reviewGroupId);
        validateAnswerByQuestion(request);
    }

    private void validateQuestionByReviewGroup(HighlightsRequest request, long reviewGroupId) {
        Set<Long> providedQuestionIds = questionRepository.findIdsByReviewGroupId(reviewGroupId);
        long submittedQuestionId = request.questionId();

        if (!providedQuestionIds.contains(submittedQuestionId)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionId, providedQuestionIds);
        }
    }

    private void validateAnswerByReviewGroup(HighlightsRequest request, long reviewGroupId) {
        Set<Long> providedAnswerIds = answerRepository.findIdsByReviewGroupId(reviewGroupId);
        List<Long> submittedAnswerIds = request.highlights()
                .stream()
                .map(HighlightRequest::answerId)
                .toList();

        if (!providedAnswerIds.containsAll(submittedAnswerIds)) {
            throw new SubmittedAnswerAndProvidedAnswerMismatchException(providedAnswerIds, submittedAnswerIds);
        }
    }

    private void validateAnswerByQuestion(HighlightsRequest request) {
        Set<Long> providedAnswerIds = answerRepository.findIdsByQuestionId(request.questionId());
        List<Long> submittedAnswerIds = request.highlights()
                .stream()
                .map(HighlightRequest::answerId)
                .toList();

        if (!providedAnswerIds.containsAll(submittedAnswerIds)) {
            throw new SubmittedAnswerAndProvidedAnswerMismatchException(providedAnswerIds, submittedAnswerIds);
        }
    }
}
