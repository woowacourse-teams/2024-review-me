package reviewme.review.service.dto.response.gathered;

import reviewme.question.domain.QuestionType;

public record SimpleQuestionResponse(
        String name,
        QuestionType type
) {
}
