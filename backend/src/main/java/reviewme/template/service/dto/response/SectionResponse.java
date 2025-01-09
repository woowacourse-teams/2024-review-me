package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;
import java.util.List;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;

public record SectionResponse(
        long sectionId,
        String sectionName,
        VisibleType visible,
        @Nullable Long onSelectedOptionId,
        String header,
        List<QuestionResponse> questions
) {

    public static SectionResponse from(Section section) {
        List<QuestionResponse> questionResponses = section.getQuestions()
                .stream()
                .map(QuestionResponse::from)
                .toList();

        return new SectionResponse(
                section.getId(),
                section.getSectionName(),
                section.getVisibleType(),
                section.isConditional() ? section.getOnSelectedOption().getId() : null,
                section.getHeader(),
                questionResponses
        );
    }
}
