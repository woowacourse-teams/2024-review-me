package reviewme.review.service.abstraction.mapper;

import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.abstraction.NewTextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;

@Component
public class NewTextAnswerMapper implements NewAnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.TEXT;
    }

    @Override
    public NewTextAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.selectedOptionIds() != null) {
            throw new TextAnswerIncludedOptionItemException(answerRequest.questionId());
        }
        return new NewTextAnswer(answerRequest.questionId(), answerRequest.text());
    }
}
