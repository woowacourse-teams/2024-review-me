package reviewme.template.service.dto.response;

import java.util.List;
import reviewme.template.domain.Template;

public record SectionNamesResponse(
        List<SectionNameResponse> sections
) {

    public static SectionNamesResponse from(Template template) {
        List<SectionNameResponse> sectionNames = template.getSections()
                .stream()
                .map(section -> new SectionNameResponse(section.getId(), section.getSectionName()))
                .toList();
        return new SectionNamesResponse(sectionNames);
    }
}
