package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.template.domain.exception.OptionItemNotFoundException;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    default OptionItem getOptionItemById(long id) {
        return findById(id).orElseThrow(() -> new OptionItemNotFoundException(id));
    }
}
