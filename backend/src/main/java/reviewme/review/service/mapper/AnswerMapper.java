package reviewme.review.service.mapper;

import reviewme.review.domain.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

public abstract class AnswerMapper {

    public final boolean supports(QuestionType questionType) {
        return getQuestionType() == questionType;
    }

    public final Answer mapToAnswer(ReviewAnswerRequest answerRequest) {
        if (!isAnswerMatchesQuestionType(answerRequest)) {
            throw new QuestionTypeAnswerMismatchException(answerRequest.questionId());
        }
        if(isAnswerEmpty(answerRequest)) {
            return null;
        }
        return doMap(answerRequest);
    }

    protected abstract Answer doMap(ReviewAnswerRequest answerRequest);

    protected abstract boolean isAnswerMatchesQuestionType(ReviewAnswerRequest request);

    protected abstract boolean isAnswerEmpty(ReviewAnswerRequest request);

    protected abstract QuestionType getQuestionType();
}
