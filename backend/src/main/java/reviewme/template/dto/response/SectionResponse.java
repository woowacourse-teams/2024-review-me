package reviewme.template.dto.response;

import java.util.List;

public record SectionResponse(

        long sectionId,
        String visible,
        Integer onSelectedOptionId,
        String header,
        List<QuestionResponse> questions
) {
}
