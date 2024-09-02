package reviewme.fixture;

import java.util.List;
import reviewme.template.domain.Template;

public class TemplateFixture {

    public static Template 템플릿(List<Long> sectionIds) {
        return new Template(sectionIds);
    }
}
