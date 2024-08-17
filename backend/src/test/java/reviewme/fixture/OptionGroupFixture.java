package reviewme.fixture;

import reviewme.question.domain.OptionGroup;

public enum OptionGroupFixture {

    카테고리_선택지_그룹(1, 2),
    꼬리_질문_선택지_그룹(1, 10),
    ;

    private final int minSelectionCount;
    private final int maxSelectionCount;

    OptionGroupFixture(int minSelectionCount, int maxSelectionCount) {
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }

    public OptionGroup createWithQuestionId(long questionId) {
        return new OptionGroup(questionId, minSelectionCount, maxSelectionCount);
    }
}
