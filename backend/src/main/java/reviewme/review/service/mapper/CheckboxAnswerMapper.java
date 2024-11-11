package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

@Component
public class CheckboxAnswerMapper extends AnswerMapper {

    @Override
    protected CheckboxAnswer doMap(ReviewAnswerRequest answerRequest) {
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }

    @Override
    protected boolean isAnswerMatchesQuestionType(ReviewAnswerRequest request) {
        return request.selectedOptionIds() != null && request.text() == null;
    }

    @Override
    protected boolean isAnswerEmpty(ReviewAnswerRequest request) {
        return request.selectedOptionIds() != null && request.selectedOptionIds().isEmpty();
    }

    @Override
    protected QuestionType getQuestionType() {
        return QuestionType.CHECKBOX;
    }
}
