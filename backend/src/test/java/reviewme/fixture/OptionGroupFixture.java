package reviewme.fixture;

import reviewme.question.domain.OptionGroup;

public enum OptionGroupFixture {

    카테고리_옵션_그룹(1, 2),
    꼬리_질문_옵션_그룹(1, 10),
    ;

    private int minSelectionCount;
    private int maxSelectionCount;

    OptionGroupFixture(int minSelectionCount, int maxSelectionCount) {
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }

    public OptionGroup create(long questionId) {
        return new OptionGroup(questionId, minSelectionCount, maxSelectionCount);
    }
}
