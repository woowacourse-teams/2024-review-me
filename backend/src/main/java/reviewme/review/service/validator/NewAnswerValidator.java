package reviewme.review.service.validator;

import reviewme.review.domain.Answer;

public interface NewAnswerValidator {

    boolean supports(Class<? extends Answer> answerClass);

    void validate(Answer answer);
}
