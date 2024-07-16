package reviewme.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewerGroupService {

    private final ReviewerGroupRepository reviewerGroupRepository;

    public ReviewerGroupResponse findReviewerGroup(long reviewerGroupId) {
        ReviewerGroup reviewerGroup = reviewerGroupRepository.getReviewerGroupById(reviewerGroupId);
        Member reviewee = reviewerGroup.getReviewee();
        return new ReviewerGroupResponse(
                reviewerGroup.getId(),
                reviewerGroup.getGroupName(),
                reviewerGroup.getDeadline(),
                new MemberResponse(reviewee.getId(), reviewee.getName())
        );
    }
}
