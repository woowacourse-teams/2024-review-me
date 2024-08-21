package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class MissingRequiredQuestionException extends BadRequestException {

    public MissingRequiredQuestionException(List<Long> missingRequiredQuestionIds) {
        super("필수 질문을 제출하지 않았어요.");
        log.warn("Required question is not submitted. Missing Required questionIds: {}",
                missingRequiredQuestionIds, this);
    }
}
