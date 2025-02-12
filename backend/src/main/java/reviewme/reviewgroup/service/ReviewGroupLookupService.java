package reviewme.reviewgroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupSummaryResponse;

@Service
@RequiredArgsConstructor
public class ReviewGroupLookupService {

    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReviewGroupSummaryResponse getReviewGroupSummary(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        // todo: 리뷰어 id 가 있다면 같이 응답해야 함
        return new ReviewGroupSummaryResponse(null, reviewGroup.getReviewee(), reviewGroup.getProjectName());
    }

    public ReviewGroupPageResponse getMyReviewGroups() {
        // TODO: 생성일자 최신순 정렬
        return null;
    }
}
