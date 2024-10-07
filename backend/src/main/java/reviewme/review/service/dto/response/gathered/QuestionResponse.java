package reviewme.review.service.dto.response.gathered;

import reviewme.question.domain.QuestionType;

public record QuestionResponse(
        String name,
        QuestionType type
) {
}
