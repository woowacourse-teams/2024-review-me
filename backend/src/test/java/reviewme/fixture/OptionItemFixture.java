package reviewme.fixture;

import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

public enum OptionItemFixture {

    카테고리_옵션_아이템1("카테고리1", OptionType.CATEGORY, 1),
    카테고리_옵션_아이템2("카테고리2", OptionType.CATEGORY, 2),
    꼬리_질문_옵션_아이템1("객관식1", OptionType.KEYWORD, 1),
    꼬리_질문_옵션_아이템2("객관식2", OptionType.KEYWORD, 2),
    ;

    private String content;
    private int position;
    private OptionType optionType;

    OptionItemFixture(String content, OptionType optionType, int position) {
        this.content = content;
        this.optionType = optionType;
        this.position = position;
    }

    public OptionItem create(long optionGroupId) {
        return new OptionItem(content, optionGroupId, position, optionType);
    }
}
