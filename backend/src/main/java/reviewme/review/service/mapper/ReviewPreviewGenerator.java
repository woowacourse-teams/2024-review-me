package reviewme.review.service.mapper;

import java.util.List;
import reviewme.review.domain.NewTextAnswer;

public class ReviewPreviewGenerator {

    private static final int PREVIEW_LENGTH = 150;
    private static final String ELLIPSIS = "...";

    public String generatePreview(List<NewTextAnswer> reviewTextAnswers) {
        if (reviewTextAnswers.isEmpty()) {
            return "";
        }
        String answer = reviewTextAnswers.get(0).getContent();
        if (answer.length() > PREVIEW_LENGTH) {
            return answer.substring(0, PREVIEW_LENGTH) + ELLIPSIS;
        }
        return answer;
    }
}
