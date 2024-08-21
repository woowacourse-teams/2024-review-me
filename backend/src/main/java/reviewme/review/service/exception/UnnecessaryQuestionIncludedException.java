package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class UnnecessaryQuestionIncludedException extends BadRequestException {

    public UnnecessaryQuestionIncludedException(List<Long> unnecessaryQuestionIds) {
        super("제출해야 할 질문 이외의 질문에 응답했습니다.");
        log.warn("Unnecessary question has submitted. unnecessaryQuestionIds: {}", unnecessaryQuestionIds, this);
    }
}
