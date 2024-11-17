package reviewme.review.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Review;
import reviewme.review.domain.policy.TemplateValidationContext;
import reviewme.review.domain.service.ReviewRegisterService;
import reviewme.review.facade.request.ReviewRegisterRequest;
import reviewme.review.facade.mapper.ReviewMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.service.StructuredTemplateService;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ReviewGroupService reviewGroupService;
    private final StructuredTemplateService structuredTemplateService;
    private final ReviewMapper reviewMapper;
    private final ReviewRegisterService reviewRegisterService;

    public long registerReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = reviewGroupService.getReviewGroupByReviewRequestCode(request.reviewRequestCode());
        StructuredTemplate template = structuredTemplateService.getStructuredTemplateById(reviewGroup.getTemplateId());

        Review review = reviewMapper.mapToReview(request.answers(), template, reviewGroup);
        Review registeredReview = reviewRegisterService.register(review, new TemplateValidationContext(template));
        return registeredReview.getId();
    }
}
