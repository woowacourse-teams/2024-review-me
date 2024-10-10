package reviewme.template.service.dto.response;

import java.util.List;

public record SectionNamesResponse(
        List<SectionNameResponse> sections
) {
}
