package reviewme.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
}
