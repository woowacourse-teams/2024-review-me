package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.exception.GroupAccessCodeNullException;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.template.domain.Template;
import reviewme.template.service.DefaultTemplateService;

@Service
@RequiredArgsConstructor
public class ReviewGroupService {

    private static final int REVIEW_REQUEST_CODE_LENGTH = 8;

    private final ReviewGroupRepository reviewGroupRepository;
    private final RandomCodeGenerator randomCodeGenerator;
    private final DefaultTemplateService defaultTemplateService;

    @Transactional
    public ReviewGroupCreationResponse createReviewGroup(ReviewGroupCreationRequest request, Long memberId) {
        String reviewRequestCode = generateReviewRequestCode();

        Template template = defaultTemplateService.getDefaultTemplate();

        ReviewGroup reviewGroup;
        if (memberId != null) {
            reviewGroup = new ReviewGroup(memberId, template.getId(), request.revieweeName(), request.projectName(),
                    reviewRequestCode);
        } else {
            if (request.groupAccessCode() == null) {
                throw new GroupAccessCodeNullException();
            }
            reviewGroup = new ReviewGroup(template.getId(), request.revieweeName(), request.projectName(),
                    reviewRequestCode, request.groupAccessCode());
        }

        reviewGroupRepository.save(reviewGroup);
        return new ReviewGroupCreationResponse(reviewGroup.getReviewRequestCode());
    }

    private String generateReviewRequestCode() {
        String reviewRequestCode;
        do {
            reviewRequestCode = randomCodeGenerator.generate(REVIEW_REQUEST_CODE_LENGTH);
        } while (reviewGroupRepository.existsByReviewRequestCode(reviewRequestCode));

        return reviewRequestCode;
    }

    @Transactional(readOnly = true)
    public ReviewGroup getReviewGroupByReviewRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }
}
