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
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Component
@RequiredArgsConstructor
public class HighlightValidator {

    private final AnswerRepository answerRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final QuestionRepository questionRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    public void validate(HighlightsRequest request, long reviewGroupId) {
        validateReviewGroupContainsQuestion(request, reviewGroupId);
        validateReviewGroupContainsAnswer(request, reviewGroupId);
        validateQuestionContainsAnswer(request);
    }

    private void validateReviewGroupContainsQuestion(HighlightsRequest request, long reviewGroupId) {
        long templateId = reviewGroupRepository.findById(reviewGroupId)
                .orElseThrow()
                .getTemplateId();
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(templateId);
        long submittedQuestionId = request.questionId();

        if (!providedQuestionIds.contains(submittedQuestionId)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionId, providedQuestionIds);
        }
    }

    private void validateReviewGroupContainsAnswer(HighlightsRequest request, long reviewGroupId) {
        Set<Long> providedAnswerIds = answerRepository.findIdsByReviewGroupId(reviewGroupId);
        List<Long> submittedAnswerIds = request.highlights()
                .stream()
                .map(HighlightRequest::answerId)
                .toList();

        if (!providedAnswerIds.containsAll(submittedAnswerIds)) {
            throw new SubmittedAnswerAndProvidedAnswerMismatchException(providedAnswerIds, submittedAnswerIds);
        }
    }

    private void validateQuestionContainsAnswer(HighlightsRequest request) {
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
