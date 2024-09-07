package reviewme.review.service.dto.response.detail;

import java.util.List;

public record SectionAnswerResponse(
        long sectionId,
        String header,
        List<QuestionAnswerResponse> questions
) {

    public boolean hasAnsweredQuestion() {
        return !questions.isEmpty();
    }
}
