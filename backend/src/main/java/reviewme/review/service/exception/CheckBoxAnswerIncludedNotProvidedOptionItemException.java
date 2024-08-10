package reviewme.review.service.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class CheckBoxAnswerIncludedNotProvidedOptionItemException extends BadRequestException {

    public CheckBoxAnswerIncludedNotProvidedOptionItemException(List<Long> providedOptionIds,
                                                                List<Long> submittedOptionIds) {
        super("제공되는 선택지에 없는 선택지를 응답했어요.");
        log.info("Answer included not provided options - providedOptionIds: {}, submittedOptionIds: {}",
                providedOptionIds, submittedOptionIds);
    }
}
