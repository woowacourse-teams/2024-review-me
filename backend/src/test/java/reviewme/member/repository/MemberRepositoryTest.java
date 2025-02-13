package reviewme.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.member.domain.Member;
import reviewme.member.domain.MemberStatus;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 회원을_저장하면_기본_설정이_적용된다() {
        // given
        Member member = new Member("email");

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertAll(
                () -> assertThat(savedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE),
                () -> assertThat(savedMember.getCreatedAt()).isNotNull(),
                () -> assertThat(savedMember.getUpdatedAt()).isNotNull()
        );
    }

    @Test
    void 회원을_수정하면_수정일자가_변경된다() {
        // given
        Member member = memberRepository.save(new Member("email"));
        LocalDateTime updatedAt = member.getUpdatedAt();

        // when
        member.deactivate();
        memberRepository.save(member);
        entityManager.flush();
        entityManager.close();

        // then
        Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getUpdatedAt()).isNotEqualTo(updatedAt);
    }
}
