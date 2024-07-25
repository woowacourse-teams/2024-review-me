package reviewme.fixture;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;

@RequiredArgsConstructor
@Getter
public enum ReviewerGroupFixture {

    데드라인_남은_그룹("데드라인 이전 그룹", "설명", LocalDateTime.now().plusDays(1)),
    데드라인_지난_그룹("데드라인 지난 그룹", "설명", LocalDateTime.now().minusDays(1)),
    ;

    private final String groupName;
    private final String description;
    private final LocalDateTime deadline;

    public ReviewerGroup create(Member reviewee, List<GithubId> reviewerGithubIds) {
        return new ReviewerGroup(reviewee, reviewerGithubIds, groupName, description, deadline);
    }
}
