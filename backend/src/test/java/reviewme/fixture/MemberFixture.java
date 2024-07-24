package reviewme.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reviewme.member.domain.Member;

@RequiredArgsConstructor
@Getter
public enum MemberFixture {

    회원_산초("산초", 1L),
    회원_아루("아루", 2L),
    ;

    private final String name;
    private final long githubId;

    public Member create() {
        return new Member(name, githubId);
    }
}
