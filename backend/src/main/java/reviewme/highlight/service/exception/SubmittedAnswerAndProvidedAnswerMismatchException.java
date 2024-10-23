package reviewme.highlight.service.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SubmittedAnswerAndProvidedAnswerMismatchException extends BadRequestException {

    public SubmittedAnswerAndProvidedAnswerMismatchException(Collection<Long> providedAnswerIds,
                                                             Collection<Long> submittedAnswerIds) {
        super("제출된 응답이 제공된 응답과 일치하지 않아요.");
        log.info("SubmittedAnswer and providedAnswer mismatch - providedAnswerIds: {}, submittedAnswerIds: {}",
                providedAnswerIds, submittedAnswerIds);
    }
}
