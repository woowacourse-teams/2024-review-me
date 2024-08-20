package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;

@Service
@RequiredArgsConstructor
public class ReviewGroupLookupService {

    private final ReviewGroupRepository reviewGroupRepository;

    public ReviewGroupResponse getReviewGroupSummary(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        return new ReviewGroupResponse(reviewGroup.getReviewee(), reviewGroup.getProjectName());
    }
}
