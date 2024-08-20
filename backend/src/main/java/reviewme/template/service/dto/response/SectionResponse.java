package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;
import java.util.List;

public record SectionResponse(
        long sectionId,
        String sectionName,
        String visible,
        @Nullable Long onSelectedOptionId,
        String header,
        List<QuestionResponse> questions
) {
}
