package reviewme.review.service.mapper;

import reviewme.question.domain.QuestionType;
import reviewme.review.domain.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

public interface AnswerMapper {

    boolean supports(QuestionType questionType);

    Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
