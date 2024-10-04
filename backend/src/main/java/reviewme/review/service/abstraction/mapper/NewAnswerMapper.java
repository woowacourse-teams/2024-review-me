package reviewme.review.service.abstraction.mapper;

import reviewme.question.domain.QuestionType;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;

public interface NewAnswerMapper {

    boolean supports(QuestionType questionType);

    Answer mapToAnswer(ReviewAnswerRequest answerRequest);
}
