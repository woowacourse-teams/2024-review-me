package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.service.exception.ReviewGroupUnAuthorizedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewGroupFinder {

    private final ReviewGroupRepository reviewGroupRepository;
    private final GroupAccessCodeEncoder groupAccessCodeEncoder;

    public ReviewGroup getByReviewRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    public ReviewGroup getByCodes(String reviewRequestCode, String groupAccessCode) {
        ReviewGroup reviewGroup = getByReviewRequestCode(reviewRequestCode);
        String encodedGroupAccessCode = groupAccessCodeEncoder.encode(groupAccessCode);
        if (!reviewGroup.hasAccessCodeOf(encodedGroupAccessCode)) {
            throw new ReviewGroupUnAuthorizedException(reviewRequestCode);
        }
        return reviewGroup;
    }
}
