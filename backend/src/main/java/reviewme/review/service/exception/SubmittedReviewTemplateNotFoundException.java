package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SubmittedReviewTemplateNotFoundException extends BadRequestException {

    public SubmittedReviewTemplateNotFoundException(long templateId) {
        super("제출된 리뷰의 템플릿이 존재하지 않아요.");
        log.warn("Submitted review's template not found. templateId: {}", templateId);
    }
}
