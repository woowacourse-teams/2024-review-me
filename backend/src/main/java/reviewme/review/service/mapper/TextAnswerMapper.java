package reviewme.review.service.mapper;

import org.springframework.stereotype.Component;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

@Component
public class TextAnswerMapper extends AnswerMapper {

    @Override
    protected TextAnswer doMap(ReviewAnswerRequest answerRequest) {
        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }

    @Override
    protected boolean isAnswerMatchesQuestionType(ReviewAnswerRequest request) {
        return request.text() != null && request.selectedOptionIds() == null;
    }

    @Override
    protected boolean isAnswerEmpty(ReviewAnswerRequest request) {
        return request.text() != null && request.text().isEmpty();
    }

    @Override
    protected QuestionType getQuestionType() {
        return QuestionType.TEXT;
    }
}
