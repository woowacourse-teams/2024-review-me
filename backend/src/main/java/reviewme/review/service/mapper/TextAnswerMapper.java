package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;

@Component
public class TextAnswerMapper implements AnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.TEXT;
    }

    @Override
    public TextAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (!answerRequest.hasTextAnswer()) {
            return null;
        }
        if (answerRequest.selectedOptionIds() != null) {
            throw new TextAnswerIncludedOptionItemException(answerRequest.questionId());
        }
        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }
}
