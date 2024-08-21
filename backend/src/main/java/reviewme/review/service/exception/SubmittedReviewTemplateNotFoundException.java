package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class SubmittedReviewTemplateNotFoundException extends DataInconsistencyException {

    public SubmittedReviewTemplateNotFoundException(long templateId) {
        super("제출된 리뷰의 템플릿이 존재하지 않아요.");
        log.error("Submitted review's template not found. templateId: {}", templateId);
    }
}
