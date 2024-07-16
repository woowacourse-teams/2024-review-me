package reviewme.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.exception.MemberNotFoundException;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMemberById(long id) {
        return findById(id).orElseThrow(MemberNotFoundException::new);
    }
}
