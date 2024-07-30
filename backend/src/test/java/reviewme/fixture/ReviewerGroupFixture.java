package reviewme.fixture;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReviewerGroupFixture {

    리뷰_그룹("리뷰 그룹", "그룹 설명", LocalDateTime.of(2024, 1, 1, 12, 0)),
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
