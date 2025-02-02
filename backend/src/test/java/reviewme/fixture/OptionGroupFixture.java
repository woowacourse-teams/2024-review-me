package reviewme.fixture;

import java.util.List;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;

public class OptionGroupFixture {

    public static OptionGroup 선택지_그룹(List<OptionItem> optionItems) {
        return new OptionGroup(optionItems, 1, optionItems.size());
    }
}
