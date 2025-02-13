package reviewme.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 회원의_상태를_INACTIVE로_변경한다() {
        // given
        Member member = new Member("email");

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.INACTIVE);
    }
}
