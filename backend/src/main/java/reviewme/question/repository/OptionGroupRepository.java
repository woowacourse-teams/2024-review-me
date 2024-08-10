package reviewme.question.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.exception.OptionGroupNotFoundException;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    Optional<OptionGroup> findByQuestionId(long questionId);

    default OptionGroup getByQuestionId(long questionId) {
          return findByQuestionId(questionId)
                  .orElseThrow(() -> new OptionGroupNotFoundException(questionId));
    }
}
