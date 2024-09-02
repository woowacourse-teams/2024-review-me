package reviewme.fixture;

import java.util.List;
import org.springframework.stereotype.Component;
import reviewme.template.domain.Template;

@Component
public class TemplateFixture {

    public static Template 템플릿(List<Long> sectionIds) {
        return new Template(sectionIds);
    }
}
