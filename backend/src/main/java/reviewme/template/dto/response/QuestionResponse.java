package reviewme.template.dto.response;

public record QuestionResponse(

        long questionId,
        boolean required,
        String content,
        String questionType,
        OptionGroupResponse optionGroup,
        boolean hasGuideline,
        String guideline
) {
}
