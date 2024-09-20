package reviewme.review.service.mapper.response;

import reviewme.question.domain.QuestionType;
import reviewme.review.domain.Answer;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;

public interface AnswerResponseMapper {

    boolean supports(QuestionType questionType);

    QuestionAnswerResponse mapToAnswerResponse(Answer answer);
}
