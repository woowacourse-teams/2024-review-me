package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SubmittedQuestionAndProvidedQuestionMismatchException extends BadRequestException {

    public SubmittedQuestionAndProvidedQuestionMismatchException(List<Long> submittedQuestionIds,
                                                                 List<Long> providedQuestionIds) {
        super("제출된 응답이 제공된 질문과 매칭되지 않아요.");
        log.info(
                "Submitted questions mismatch with provided questions - submittedQuestionIds: {}, providedQuestionIds: {}",
                submittedQuestionIds, providedQuestionIds);
    }
}
