package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;

public record QuestionResponse(
        long questionId,
        boolean required,
        String content,
        QuestionType questionType,
        @Nullable OptionGroupResponse optionGroup,
        boolean hasGuideline,
        @Nullable String guideline
) {

    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.isRequired(),
                question.getContent(),
                question.getQuestionType(),
                question.isCheckbox() ? OptionGroupResponse.from(question.getOptionGroup()) : null,
                question.hasGuideline(),
                question.getGuideline()
        );
    }
}
