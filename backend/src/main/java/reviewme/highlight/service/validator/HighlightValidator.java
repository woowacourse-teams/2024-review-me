package reviewme.highlight.service.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.HighlightPosition;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.exception.HighlightDuplicatedException;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.highlight.service.exception.SubmittedAnswerAndProvidedAnswerMismatchException;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.exception.AnswerNotFoundByIdException;
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
        validateLineIndex(request);
        validateDuplicate(request);
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

    private void validateLineIndex(HighlightsRequest request) {
        for (HighlightRequest highlight : request.highlights()) {
            TextAnswer textAnswer = textAnswerRepository.findById(highlight.answerId())
                    .orElseThrow(() -> new AnswerNotFoundByIdException(highlight.answerId()));
            long maxLineIndex = textAnswer.getContent().lines().count();

            for (HighlightedLineRequest line : highlight.lines()) {
                long submittedLineIndex = line.index();
                if (maxLineIndex < submittedLineIndex) {
                    throw new InvalidHighlightLineIndexException(submittedLineIndex, maxLineIndex);
                }
            }
        }
    }

    private void validateDuplicate(HighlightsRequest request) {
        Set<HighlightPosition> uniqueHighlights = new HashSet<>();

        request.highlights().forEach(highlight ->
                highlight.lines().forEach(line ->
                        line.ranges().forEach(range -> {
                            HighlightPosition key = new HighlightPosition(line.index(),
                                    range.startIndex(), range.endIndex());
                            if (!uniqueHighlights.add(key)) {
                                throw new HighlightDuplicatedException(
                                        highlight.answerId(), line.index(), range.startIndex(), range.endIndex()
                                );
                            }
                        })
                )
        );
    }
}
