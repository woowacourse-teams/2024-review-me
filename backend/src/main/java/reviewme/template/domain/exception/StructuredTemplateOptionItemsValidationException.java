package reviewme.template.domain.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateOptionItemsValidationException extends BadRequestException {

    public StructuredTemplateOptionItemsValidationException(Collection<Long> optionGroupIds,
                                                            Collection<Long> optionGroupIdsOfItems) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("OptionItem validation failed while creating StructuredTemplate: optionGroupIds: {}, optionGroupIdsOfItems: {}",
                optionGroupIds, optionGroupIdsOfItems);
    }
}
