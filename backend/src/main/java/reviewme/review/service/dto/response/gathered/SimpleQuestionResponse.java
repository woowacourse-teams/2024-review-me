package reviewme.review.service.dto.response.gathered;

import reviewme.question.domain.QuestionType;

public record SimpleQuestionResponse(
        long id,
        String name,
        QuestionType type
) {
}
