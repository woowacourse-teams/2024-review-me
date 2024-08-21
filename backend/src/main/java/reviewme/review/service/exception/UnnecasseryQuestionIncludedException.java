package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class UnnecasseryQuestionIncludedException extends BadRequestException {

    public UnnecasseryQuestionIncludedException(List<Long> unnecessaryQuestionIds) {
        super("제출해야 할 질문 이외의 질문에 응답했습니다.");
        log.warn("Unnecessery question is submitted. unnecessaryQuestionIds: {}", unnecessaryQuestionIds, this);
    }
}
