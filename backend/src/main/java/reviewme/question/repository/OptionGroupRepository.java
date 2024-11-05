package reviewme.question.repository;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    @Cacheable(value = "templateCache", key = "#questionId")
    Optional<OptionGroup> findByQuestionId(long questionId);
}
