package reviewme.fixture;

import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

public class OptionItemFixture {

    public OptionItem 선택지(long optionGroupId) {
        return new OptionItem("선택지 본문", optionGroupId, 1, OptionType.CATEGORY);
    }

    public OptionItem 선택지(long optionGroupId, int position) {
        return new OptionItem("선택지 본문", optionGroupId, 1, OptionType.CATEGORY);
    }
}
