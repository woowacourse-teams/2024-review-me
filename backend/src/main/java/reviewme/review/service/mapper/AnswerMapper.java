package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

@Component
public class AnswerMapper {

    public TextAnswer mapToTextAnswer(ReviewAnswerRequest answerRequest) {
        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }

    public CheckboxAnswer mapToCheckBoxAnswer(ReviewAnswerRequest answerRequest) {
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
