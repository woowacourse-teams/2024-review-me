package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.NewCheckboxAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;

@Component
public class NewCheckboxAnswerMapper implements NewAnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.CHECKBOX;
    }

    @Override
    public NewCheckboxAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.text() != null) {
            throw new CheckBoxAnswerIncludedTextException(answerRequest.questionId());
        }
        return new NewCheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
