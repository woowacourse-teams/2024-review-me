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

    protected abstract QuestionType getQuestionType();

    private boolean isAnswerMatchesQuestionType(ReviewAnswerRequest request) {
        if (getQuestionType() == QuestionType.CHECKBOX) {
            return request.selectedOptionIds() != null && request.text() == null;
        }
        return request.text() != null && request.selectedOptionIds() == null;
    }

    private boolean isAnswerEmpty(ReviewAnswerRequest request) {
        if (getQuestionType() == QuestionType.CHECKBOX) {
            return request.selectedOptionIds() != null && request.selectedOptionIds().isEmpty();
        }
        return request.text() != null && request.text().isEmpty();
    }
}
