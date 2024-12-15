package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

@Component
public class CheckboxAnswerMapper implements AnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.CHECKBOX;
    }

    @Override
    public CheckboxAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.hasNoSelectedOptions()) {
            return null;
        }
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
