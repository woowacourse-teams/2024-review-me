package reviewme.review.service.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SubmittedQuestionAndProvidedQuestionMismatchException extends BadRequestException {

    public SubmittedQuestionAndProvidedQuestionMismatchException(Collection<Long> submittedQuestionIds,
                                                                 Collection<Long> providedQuestionIds) {
        super("제출된 응답이 제공된 질문과 매칭되지 않아요.");
        log.warn(
                "Submitted questions and provided questions mismatch. submittedQuestionIds: {}, providedQuestionIds: {}",
                submittedQuestionIds, providedQuestionIds
        );
    }
}
