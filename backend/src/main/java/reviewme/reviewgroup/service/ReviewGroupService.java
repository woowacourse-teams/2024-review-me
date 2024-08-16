package reviewme.reviewgroup.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewGroupService {

    private static final int REVIEW_REQUEST_CODE_LENGTH = 8;
    private static final int GROUP_ACCESS_CODE_LENGTH = 8;

    private final ReviewGroupRepository reviewGroupRepository;
    private final RandomCodeGenerator randomCodeGenerator;

    @Transactional
    public ReviewGroupCreationResponse createReviewGroup(ReviewGroupCreationRequest request) {
        String reviewRequestCode;
        String groupAccessCode;
        do {
            reviewRequestCode = randomCodeGenerator.generate(REVIEW_REQUEST_CODE_LENGTH);
        } while (reviewGroupRepository.existsByReviewRequestCode(reviewRequestCode));
        do {
            groupAccessCode = randomCodeGenerator.generate(GROUP_ACCESS_CODE_LENGTH);
        } while (reviewGroupRepository.existsByGroupAccessCode(groupAccessCode));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup(request.revieweeName(), request.projectName(), reviewRequestCode, groupAccessCode)
        );
        return new ReviewGroupCreationResponse(reviewGroup.getReviewRequestCode(), reviewGroup.getGroupAccessCode());
    }
}
