package reviewme.review.domain.policy;

import reviewme.review.domain.Answer;

public interface AnswerRegistrationPolicy {

    boolean support(Answer answer);

    void verify(Answer answer, TemplateValidationContext templateContext);
}
