package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;

@Component
public class CheckboxAnswerMapper implements AnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.CHECKBOX;
    }

    @Override
    public CheckboxAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.selectedOptionIds() == null || answerRequest.selectedOptionIds().isEmpty()) {
            return null;
        }

        if (answerRequest.text() != null) {
            throw new CheckBoxAnswerIncludedTextException(answerRequest.questionId());
        }
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
