package reviewme.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;

@RequiredArgsConstructor
@Getter
public enum MemberFixture {

    회원_산초(1001L, "산초", 1L),
    회원_아루(1002L, "아루", 2L),
    회원_커비(1003L, "커비", 3L),
    회원_테드(1004L, "테드", 4L),
    ;

    private final long id;
    private final String name;
    private final long githubId;

    public Member create() {
        return new Member(id, name, new GithubId(githubId));
    }
}
