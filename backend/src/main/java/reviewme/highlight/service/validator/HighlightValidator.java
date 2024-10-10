package reviewme.highlight.service.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.exception.HighlightDuplicatedException;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.highlight.service.exception.InvalidHighlightRangeException;
import reviewme.highlight.service.exception.SubmittedAnswerAndProvidedAnswerMismatchException;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.exception.AnswerNotFoundByIdException;
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
        validateLineIndex(request);
        validateRange(request);
        validateDuplicate(request);
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

    private void validateQuestionByReviewGroup(HighlightsRequest request, long reviewGroupId) {
        Set<Long> providedQuestionIds = questionRepository.findIdsByReviewGroupId(reviewGroupId);
        long submittedQuestionId = request.questionId();

        if (!providedQuestionIds.contains(submittedQuestionId)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionId, providedQuestionIds);
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

    private void validateRange(HighlightsRequest request) {
        request.highlights()
                .stream()
                .flatMap(highlightRequest -> highlightRequest.lines().stream()
                        .flatMap(highlightedLineRequest -> highlightedLineRequest.ranges().stream()
                                .filter(range -> range.startIndex() > range.endIndex())
                        )
                )
                .findFirst()
                .ifPresent(range -> {
                    throw new InvalidHighlightRangeException(range.startIndex(), range.endIndex());
                });
    }

    private void validateDuplicate(HighlightsRequest request) {
        Set<HighlightKey> uniqueHighlights = new HashSet<>();

        request.highlights().forEach(highlight ->
                highlight.lines().forEach(line ->
                        line.ranges().forEach(range -> {
                            HighlightKey key = new HighlightKey(
                                    highlight.answerId(), line.index(), range.startIndex(), range.endIndex()
                            );
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
