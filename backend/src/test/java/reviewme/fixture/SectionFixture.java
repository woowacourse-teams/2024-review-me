package reviewme.fixture;

import java.util.List;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;

public class SectionFixture {

    public static Section 항상_보이는_섹션(List<Long> questionIds) {
        return 항상_보이는_섹션(questionIds, 1);
    }

    public static Section 항상_보이는_섹션(List<Long> questionIds, int position) {
        return new Section(VisibleType.ALWAYS, questionIds, null, "섹션명", "머릿말", position);
    }

    public static Section 조건부로_보이는_섹션(List<Long> questionIds, long onSelectedOptionId) {
        return 조건부로_보이는_섹션(questionIds, onSelectedOptionId, 1);
    }

    public static Section 조건부로_보이는_섹션(List<Long> questionIds, long onSelectedOptionId, int position) {
        return new Section(VisibleType.CONDITIONAL, questionIds, onSelectedOptionId, "섹션명", "머릿말", position);
    }
}
