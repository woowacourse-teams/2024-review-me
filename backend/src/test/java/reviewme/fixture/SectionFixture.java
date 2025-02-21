package reviewme.fixture;

import java.util.List;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;

public class SectionFixture {

    public static Section 항상_보이는_섹션(List<Question> questions) {
        return 항상_보이는_섹션(questions, 1);
    }

    public static Section 항상_보이는_섹션(List<Question> questions, int position) {
        return new Section(VisibleType.ALWAYS, questions, null, "섹션명", "머릿말", position);
    }

    public static Section 조건부로_보이는_섹션(List<Question> questions, OptionItem onSelectedOption) {
        return 조건부로_보이는_섹션(questions, onSelectedOption, 1);
    }

    public static Section 조건부로_보이는_섹션(List<Question> questions, OptionItem onSelectedOption, int position) {
        return new Section(VisibleType.CONDITIONAL, questions, onSelectedOption, "섹션명", "머릿말", position);
    }
}
