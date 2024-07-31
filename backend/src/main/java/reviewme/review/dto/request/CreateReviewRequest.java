package reviewme.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateReviewRequest(

        @NotBlank
        String reviewRequestCode,

        @NotNull
        List<CreateReviewContentRequest> reviewContents,

        @NotNull
        List<Long> keywords
) {
}
