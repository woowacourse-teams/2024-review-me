package reviewme.fixture;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;

@RequiredArgsConstructor
public enum ReviewerGroupFixture {

    리뷰_그룹("리뷰 그룹", "그룹 설명", LocalDateTime.of(2024, 1, 1, 12, 0)),
    ;

    private final String groupName;
    private final String description;
    private final LocalDateTime createdAt;

    public ReviewerGroup create(Member reviewee) {
        return new ReviewerGroup(reviewee, groupName, description, createdAt);
    }
}
