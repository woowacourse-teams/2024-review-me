package reviewme.review.service.exception;

import java.util.List;
import reviewme.global.exception.BadRequestException;

public class MissingRequiredQuestionException extends BadRequestException {

    public MissingRequiredQuestionException(List<Long> missingRequiredQuestionIds) {
        super("필수 질문을 제출하지 않았어요.",
                "Required question is not submitted. Missing Required questionIds: %s"
                        .formatted(missingRequiredQuestionIds));
    }
}
