package reviewme.question.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.exception.MissingOptionGroupForQuestionException;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    Optional<OptionGroup> findByQuestionId(long questionId);

    default OptionGroup getByQuestionId(long questionId) {
        return findByQuestionId(questionId)
                .orElseThrow(() -> new MissingOptionGroupForQuestionException(questionId));
    }
}
