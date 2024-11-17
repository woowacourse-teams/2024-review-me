package reviewme.template.service.exception;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateOptionItemsValidationException extends BadRequestException {

    public StructuredTemplateOptionItemsValidationException(List<Long> optionGroupIds, Set<Long> itemGroupIds) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("OptionItem validation failed during StructuredTemplate creation: optionGroupIds: {}, itemGroupIds: {}",
                optionGroupIds, itemGroupIds);
    }
}
