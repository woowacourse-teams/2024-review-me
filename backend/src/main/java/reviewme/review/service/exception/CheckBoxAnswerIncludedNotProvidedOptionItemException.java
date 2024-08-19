package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnexpectedRequestException;

@Slf4j
public class CheckBoxAnswerIncludedNotProvidedOptionItemException extends UnexpectedRequestException {

    public CheckBoxAnswerIncludedNotProvidedOptionItemException(long questionId,
                                                                List<Long> providedOptionIds,
                                                                List<Long> submittedOptionIds) {
        super("제공되는 선택지에 없는 선택지를 응답했어요.");
        log.warn("Answer included not provided options - questionId:{}, providedOptionIds: {}, submittedOptionIds: {}",
                questionId, providedOptionIds, submittedOptionIds, this);
    }
}
