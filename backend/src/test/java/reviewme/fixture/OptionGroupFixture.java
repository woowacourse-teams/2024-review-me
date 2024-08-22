package reviewme.fixture;

import reviewme.question.domain.OptionGroup;

public class OptionGroupFixture {

    public OptionGroup 선택지_그룹(long questionId) {
        return new OptionGroup(questionId, 1, 2);
    }
}
