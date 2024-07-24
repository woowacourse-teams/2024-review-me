package reviewme.fixture;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;

@RequiredArgsConstructor
public enum ReviewerGroupFixture {

    리뷰_그룹("리뷰 그룹", "그룹 설명", LocalDateTime.of(2024, 1, 1, 12, 0)),
    ;

    private final String groupName;
    private final String description;
    private final LocalDateTime deadline;

    public ReviewerGroup create(Member reviewee, List<GithubId> reviewerGithubIds) {
        return new ReviewerGroup(reviewee, reviewerGithubIds, groupName, description, deadline);
    }
}
