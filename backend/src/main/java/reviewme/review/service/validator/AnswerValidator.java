package reviewme.review.service.validator;

import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.service.exception.QuestionNotContainingAnswersException;
import reviewme.review.service.exception.ReviewGroupNotContainingAnswersException;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
@RequiredArgsConstructor
public class AnswerValidator {

    private final AnswerRepository answerRepository;

    public void validateQuestionContainsAnswers(long questionId, Collection<Long> answerIds) {
        Set<Long> receivedAnswerIds = answerRepository.findIdsByQuestionId(questionId);
        if (!receivedAnswerIds.containsAll(answerIds)) {
            throw new QuestionNotContainingAnswersException(questionId, answerIds);
        }
    }

    public void validateReviewGroupContainsAnswers(ReviewGroup reviewGroup, Collection<Long> answerIds) {
        Set<Long> receivedAnswerIds = answerRepository.findIdsByReviewGroupId(reviewGroup.getId());
        if (!receivedAnswerIds.containsAll(answerIds)) {
            throw new ReviewGroupNotContainingAnswersException(reviewGroup.getId(), answerIds);
        }
    }
}
