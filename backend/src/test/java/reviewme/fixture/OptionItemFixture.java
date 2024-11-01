package reviewme.fixture;

import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;

public class OptionItemFixture {

    public static OptionItem 선택지(long optionGroupId) {
        return 선택지(optionGroupId, 1);
    }

    public static OptionItem 선택지(long optionGroupId, int position) {
        return new OptionItem("선택지 본문", optionGroupId, position, OptionType.CATEGORY);
    }
}
