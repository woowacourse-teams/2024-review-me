package reviewme.review.service.dto.response.gathered;

import reviewme.template.domain.QuestionType;

public record SimpleQuestionResponse(
        long id,
        String name,
        QuestionType type
) {
}
