package reviewme.review.facade.mapper;

import reviewme.review.domain.Answer;
import reviewme.review.facade.request.ReviewAnswerRequest;
import reviewme.template.domain.QuestionType;

public interface AnswerMapper {

    boolean supports(QuestionType questionType);

    Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
