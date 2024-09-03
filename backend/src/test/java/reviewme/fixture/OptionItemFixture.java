package reviewme.fixture;

import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

public class OptionItemFixture {

    public static  OptionItem 선택지(long optionGroupId) {
        return 선택지(optionGroupId, 1);
    }

    public static OptionItem 선택지(long optionGroupId, int position) {
        return new OptionItem("선택지 본문", optionGroupId, 1, OptionType.CATEGORY);
    }
}
