package reviewme.fixture;

import reviewme.member.domain.Member;

public class MemberFixture {

    public static Member 회원() {
        return new Member("email@email.com");
    }

    public static Member 회원(String email) {
        return new Member(email);
    }
}
