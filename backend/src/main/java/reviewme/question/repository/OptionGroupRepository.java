package reviewme.question.repository;

import java.util.Optional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;

@Repository
@CacheConfig(cacheNames = "templateCache", keyGenerator = "cacheKeyGenerator")
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    @Cacheable
    Optional<OptionGroup> findByQuestionId(long questionId);
}
