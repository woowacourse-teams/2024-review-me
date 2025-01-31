package reviewme.template.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.OptionGroup;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    @Query("""
            SELECT og FROM Question q
            JOIN q.optionGroup og
            WHERE q.id = :questionId
            """)
    Optional<OptionGroup> findByQuestionId(long questionId);
}
