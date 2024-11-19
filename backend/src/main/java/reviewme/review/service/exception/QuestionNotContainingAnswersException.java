package reviewme.review.service.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.ReviewMeException;

@Slf4j
public class QuestionNotContainingAnswersException extends ReviewMeException {

    public QuestionNotContainingAnswersException(long questionId, Collection<Long> providedAnswerIds) {
        super("질문에 속하지 않는 답변이예요.");
        log.info("Question not containing provided answers - questionId: {}, providedAnswerIds: {}",
                questionId, providedAnswerIds);
    }
}
