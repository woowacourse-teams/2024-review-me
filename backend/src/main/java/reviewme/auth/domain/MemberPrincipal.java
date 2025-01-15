package reviewme.auth.domain;

import lombok.Getter;
import reviewme.member.domain.Member;

@Getter
public class MemberPrincipal extends Principal {

    private final Member member;

    public MemberPrincipal(Member member) {
        this.member = member;
    }
}
