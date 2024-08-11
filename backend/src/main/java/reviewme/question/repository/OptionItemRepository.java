package reviewme.question.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.template.domain.exception.OptionItemNotFoundException;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    List<OptionItem> findAllByOptionType(OptionType optionType);

    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    boolean existsByOptionTypeAndId(OptionType optionType, long id);

    default OptionItem getOptionItemById(long id) {
        return findById(id).orElseThrow(() -> new OptionItemNotFoundException(id));
    }
}
