package reviewme.question.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    Optional<OptionGroup> findByQuestionId(long questionId);

    @Query(value = """
            SELECT og.* FROM option_group og
            WHERE og.question_id IN (:questionIds)
            """, nativeQuery = true)
    List<OptionGroup> findAllByQuestionIds(List<Long> questionIds);
}
