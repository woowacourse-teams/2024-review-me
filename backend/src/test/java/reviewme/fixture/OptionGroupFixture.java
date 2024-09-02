package reviewme.fixture;

import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;

@Component
public class OptionGroupFixture {

    public static OptionGroup 선택지_그룹(long questionId) {
        return new OptionGroup(questionId, 1, 2);
    }
}
