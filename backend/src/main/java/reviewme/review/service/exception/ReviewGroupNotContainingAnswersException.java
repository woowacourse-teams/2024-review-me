package reviewme.review.service.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.ReviewMeException;

@Slf4j
public class ReviewGroupNotContainingAnswersException extends ReviewMeException {

    public ReviewGroupNotContainingAnswersException(long reviewGroupId, Collection<Long> providedAnswerIds) {
        super("리뷰 그룹에 속하지 않는 답변이예요.");
        log.info("ReviewGroup not containing provided answers - reviewGroupId: {}, providedAnswerIds: {}",
                reviewGroupId, providedAnswerIds);
    }
}
