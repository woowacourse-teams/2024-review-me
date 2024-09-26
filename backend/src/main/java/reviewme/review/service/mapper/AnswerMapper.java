package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;

@Component
public class AnswerMapper {

    public TextAnswer mapToTextAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.selectedOptionIds() != null) {
            throw new TextAnswerIncludedOptionItemException(answerRequest.questionId());
        }

        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }

    public CheckboxAnswer mapToCheckBoxAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.text() != null) {
            throw new CheckBoxAnswerIncludedTextException(answerRequest.questionId());
        }

        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
