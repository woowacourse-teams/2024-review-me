package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.reviewgroup.service.dto.CheckValidAccessResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;

@Service
@RequiredArgsConstructor
public class ReviewGroupService {

    private static final int REVIEW_REQUEST_CODE_LENGTH = 8;

    private final ReviewGroupRepository reviewGroupRepository;
    private final RandomCodeGenerator randomCodeGenerator;
    private final GroupAccessCodeEncoder groupAccessCodeEncoder;

    @Transactional
    public ReviewGroupCreationResponse createReviewGroup(ReviewGroupCreationRequest request) {
        String encodedGroupAccessCode = groupAccessCodeEncoder.encode(request.groupAccessCode());
        String reviewRequestCode;
        do {
            reviewRequestCode = randomCodeGenerator.generate(REVIEW_REQUEST_CODE_LENGTH);
        } while (reviewGroupRepository.existsByReviewRequestCode(reviewRequestCode));

        ReviewGroup reviewGroup = new ReviewGroup(
                request.revieweeName(), request.projectName(), reviewRequestCode, encodedGroupAccessCode
        );
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(reviewGroup);
        return new ReviewGroupCreationResponse(savedReviewGroup.getReviewRequestCode());
    }

    @Transactional(readOnly = true)
    public CheckValidAccessResponse checkGroupAccessCode(CheckValidAccessRequest request) {
        boolean hasAccess = reviewGroupRepository.existsByReviewRequestCodeAndGroupAccessCode(
                request.reviewRequestCode(), request.groupAccessCode()
        );
        return new CheckValidAccessResponse(hasAccess);
    }
}
