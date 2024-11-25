package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

@Component
public class TextAnswerMapper implements AnswerMapper {

    @Override
    public boolean supports(QuestionType questionType) {
        return questionType == QuestionType.TEXT;
    }

    @Override
    public TextAnswer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (answerRequest.hasNoText()) {
            return null;
        }
        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }
}
