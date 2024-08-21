package reviewme.review.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateReviewRequest(

        @NotBlank(message = "리뷰 요청 코드를 입력해주세요.")
        String reviewRequestCode,

        @NotEmpty(message = "답변 내용을 입력해주세요.")
        List<CreateReviewAnswerRequest> answers
) {
}
