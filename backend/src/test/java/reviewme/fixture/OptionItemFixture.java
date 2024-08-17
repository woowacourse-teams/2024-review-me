package reviewme.fixture;

import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

public enum OptionItemFixture {

    카테고리_선택지("카테고리 선택지", 1L, OptionType.CATEGORY, 1),
    꼬리_질문_선택지("꼬리질문 선택지", 2L, OptionType.KEYWORD, 1),
    ;

    private final String content;
    private final long optionGroupId;
    private final int position;
    private final OptionType optionType;

    OptionItemFixture(String content, long optionGroupId, OptionType optionType, int position) {
        this.content = content;
        this.optionGroupId = optionGroupId;
        this.optionType = optionType;
        this.position = position;
    }

    public OptionItem create() {
        return new OptionItem(this.content, this.optionGroupId, this.position, this.optionType);
    }

    public OptionItem createWithOptionGroupId(long optionGroupId) {
        return new OptionItem(this.content, optionGroupId, this.position, this.optionType);
    }

    public OptionItem createWithPosition(int position) {
        return new OptionItem(this.content, this.optionGroupId, position, this.optionType);
    }

    public OptionItem createWithOptionGroupIdAndPosition(long optionGroupId, int position) {
        return new OptionItem(this.content, optionGroupId, position, this.optionType);
    }
}
