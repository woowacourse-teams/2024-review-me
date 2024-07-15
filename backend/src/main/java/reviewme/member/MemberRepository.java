package reviewme.member;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMemberById(long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
