package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;
import java.util.List;
import reviewme.template.domain.VisibleType;

public record SectionResponse(
        long sectionId,
        String sectionName,
        VisibleType visible,
        @Nullable Long onSelectedOptionId,
        String header,
        List<QuestionResponse> questions
) {
}
