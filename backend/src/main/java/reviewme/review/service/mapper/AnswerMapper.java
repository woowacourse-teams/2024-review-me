package reviewme.review.service.mapper;

import reviewme.review.domain.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

public abstract class AnswerMapper {

    protected abstract boolean supports(QuestionType questionType);

    protected abstract Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
