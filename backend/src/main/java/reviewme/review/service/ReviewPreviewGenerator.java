package reviewme.review.service;

import java.util.List;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.TextAnswer;

public class ReviewPreviewGenerator {

    private static final int PREVIEW_LENGTH = 150;

    public String generatePreview(List<ReviewContent> reviewContents) {
        if (reviewContents.isEmpty()) {
            return "";
        }
        String answer = reviewContents.get(0).getAnswer();
        if (answer.length() > PREVIEW_LENGTH) {
            return answer.substring(0, PREVIEW_LENGTH);
        }
        return answer;
    }

    public String generatePreview2(List<TextAnswer> reviewTextAnswers) {
        if (reviewTextAnswers.isEmpty()) {
            return "";
        }
        String answer = reviewTextAnswers.get(0).getContent();
        if (answer.length() > PREVIEW_LENGTH) {
            return answer.substring(0, PREVIEW_LENGTH);
        }
        return answer;
    }
}
