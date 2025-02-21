package reviewme.template.domain;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidSelectionRangeException extends BadRequestException {

    public InvalidSelectionRangeException(int size, int minSelectionCount, int maxSelectionCount) {
        super("선택 가능 범위가 잘못 설정되었어요.");
        log.info("Invalid selection range on OptionGroup: OptionGroup size={}, minSelectionCount={}, maxSelectionCount={}",
                size, minSelectionCount, maxSelectionCount);
    }

    public InvalidSelectionRangeException(int minSelectionCount, int maxSelectionCount) {
        super("선택 가능 범위가 잘못 설정되었어요.");
        log.info("Invalid selection range: minSelectionCount={}, maxSelectionCount={}",
                minSelectionCount, maxSelectionCount);
    }
}
