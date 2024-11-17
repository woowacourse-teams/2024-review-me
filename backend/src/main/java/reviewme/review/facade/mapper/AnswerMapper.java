package reviewme.review.facade.mapper;

import reviewme.template.domain.QuestionType;
import reviewme.review.domain.Answer;
import reviewme.review.facade.request.ReviewAnswerRequest;

public interface AnswerMapper {

    boolean supports(QuestionType questionType);

    Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
