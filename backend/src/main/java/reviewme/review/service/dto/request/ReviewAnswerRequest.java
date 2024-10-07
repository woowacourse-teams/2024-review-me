package reviewme.review.service.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@OneTypeAnswer(message = "한 가지 유형의 답변만 가능해요.")
public record ReviewAnswerRequest(

        @NotNull(message = "질문 ID를 입력해주세요.")
        Long questionId,

        @Nullable
        List<Long> selectedOptionIds,

        @Nullable
        String text
) {
    public boolean hasTextAnswer() {
        return text != null && !text.isEmpty();
    }

    public boolean hasCheckboxAnswer() {
        return selectedOptionIds != null && !selectedOptionIds.isEmpty();
    }
}
