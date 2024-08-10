package reviewme.review.dto.request.create;

import java.util.List;

public record CreateReviewRequest (
        String reviewRequestCode,
        List<CreateReviewAnswerRequest> answers
){
}
