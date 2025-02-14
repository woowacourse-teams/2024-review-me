package reviewme.reviewgroup.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupSummaryResponse;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.util.PageSize;

@Service
@RequiredArgsConstructor
public class ReviewGroupLookupService {

    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional(readOnly = true)
    public ReviewGroupSummaryResponse getReviewGroupSummary(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        return new ReviewGroupSummaryResponse(
                reviewGroup.getMemberId(), reviewGroup.getReviewee(), reviewGroup.getProjectName());
    }

    @Transactional(readOnly = true)
    public ReviewGroupPageResponse getReviewGroupsByMember(@Nullable Long lastReviewGroupId, @Nullable Integer size,
                                                           long memberId) {
        PageSize pageSize = new PageSize(size);
        List<ReviewGroupPageElementResponse> elements = reviewGroupRepository.findByMemberIdWithLimit(
                memberId, lastReviewGroupId, pageSize.getSize() + 1);

        boolean isLastPage = elements.size() <= pageSize.getSize();
        if (!isLastPage) {
            elements = elements.subList(0, pageSize.getSize());
        }

        long newLastReviewGroupId = (!elements.isEmpty()) ? elements.get(elements.size() - 1).reviewGroupId() : 0;
        return new ReviewGroupPageResponse(newLastReviewGroupId, isLastPage, elements);
    }
}
