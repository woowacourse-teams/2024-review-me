package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundException;

@Service
@RequiredArgsConstructor
public class ReviewGroupService {

    private static final int REVIEW_REQUEST_CODE_LENGTH = 8;
    private static final long DEFAULT_TEMPLATE_ID = 1L;

    private final ReviewGroupRepository reviewGroupRepository;
    private final RandomCodeGenerator randomCodeGenerator;
    private final TemplateRepository templateRepository;

    @Transactional
    public ReviewGroupCreationResponse createReviewGroup(ReviewGroupCreationRequest request) {
        String reviewRequestCode;
        do {
            reviewRequestCode = randomCodeGenerator.generate(REVIEW_REQUEST_CODE_LENGTH);
        } while (reviewGroupRepository.existsByReviewRequestCode(reviewRequestCode));

        Template template = templateRepository.findById(DEFAULT_TEMPLATE_ID)
                .orElseThrow(() -> new TemplateNotFoundException(DEFAULT_TEMPLATE_ID));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup(
                        request.revieweeName(), request.projectName(), reviewRequestCode, request.groupAccessCode(),
                        template.getId()
                )
        );
        return new ReviewGroupCreationResponse(reviewGroup.getReviewRequestCode());
    }

    @Transactional(readOnly = true)
    public void checkGroupAccessCode(CheckValidAccessRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(request.reviewRequestCode())
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(request.reviewRequestCode()));
        if (!reviewGroup.matchesGroupAccessCode(request.groupAccessCode())) {
            throw new ReviewGroupUnauthorizedException(reviewGroup.getId());
        }
    }

    @Transactional(readOnly = true)
    public ReviewGroup getReviewGroupByReviewRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }
}
