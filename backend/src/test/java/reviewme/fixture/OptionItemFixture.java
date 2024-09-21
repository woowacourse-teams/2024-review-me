package reviewme.fixture;

import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

public class OptionItemFixture {

    public static OptionItem 선택지_카테고리(long optionGroupId) {
        return 선택지_카테고리(optionGroupId, 1);
    }

    public static OptionItem 선택지_카테고리(long optionGroupId, int position) {
        return new OptionItem("선택지_카테고리 본문", optionGroupId, position, OptionType.CATEGORY);
    }

    public static OptionItem 선택지_키워드(long optionGroupId) {
        return 선택지_키워드(optionGroupId, 1);
    }

    public static OptionItem 선택지_키워드(long optionGroupId, int position) {
        return new OptionItem("선택지 본문", optionGroupId, position, OptionType.KEYWORD);
    }
}
