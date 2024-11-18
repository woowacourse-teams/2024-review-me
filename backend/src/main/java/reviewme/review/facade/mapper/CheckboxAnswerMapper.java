package reviewme.review.facade.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.facade.request.ReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.template.domain.QuestionType;

@Component
public class CheckboxAnswerMapper implements AnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.CHECKBOX;
    }

    @Override
    public CheckboxAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (!answerRequest.hasCheckboxAnswer()) {
            return null;
        }
        if (answerRequest.text() != null) {
            throw new CheckBoxAnswerIncludedTextException(answerRequest.questionId());
        }
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
