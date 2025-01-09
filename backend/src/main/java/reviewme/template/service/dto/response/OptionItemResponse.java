package reviewme.template.service.dto.response;

import reviewme.template.domain.OptionItem;

public record OptionItemResponse(
        long optionId,
        String content
) {

    public static OptionItemResponse from(OptionItem optionItem) {
        return new OptionItemResponse(optionItem.getId(), optionItem.getContent());
    }
}
