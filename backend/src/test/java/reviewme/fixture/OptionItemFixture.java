package reviewme.fixture;

import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;

public class OptionItemFixture {

    public static OptionItem 선택지() {
        return new OptionItem("선택지 본문", 1, OptionType.CATEGORY);
    }
}
