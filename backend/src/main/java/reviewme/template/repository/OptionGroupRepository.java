package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionGroup;
import reviewme.template.domain.exception.OptionGroupNotFoundException;

@Repository
public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {

    default OptionGroup getOptionGroupById(long id) {
        return findById(id).orElseThrow(() -> new OptionGroupNotFoundException(id));
    }
}
