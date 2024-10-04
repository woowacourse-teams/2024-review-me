package reviewme.review.service.abstraction.validator;

import reviewme.review.domain.abstraction.Answer;

public interface NewAnswerValidator {

    boolean supports(Class<? extends Answer> answerClass);

    void validate(Answer answer);
}
