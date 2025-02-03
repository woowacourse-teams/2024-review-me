package reviewme.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
}
