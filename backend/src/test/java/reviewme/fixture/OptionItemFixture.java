package reviewme.fixture;

import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

@Component
public class OptionItemFixture {

    public static OptionItem 선택지(long optionGroupId) {
        return 선택지(optionGroupId, 1);
    }

    public static OptionItem 선택지(long optionGroupId, int position) {
        return new OptionItem("선택지 본문", optionGroupId, position, OptionType.CATEGORY);
    }
}
