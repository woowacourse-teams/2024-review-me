package reviewme.review.service.mapper;

import reviewme.review.domain.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

public abstract class AnswerMapper {

    public abstract boolean supports(QuestionType questionType);

    public abstract Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
