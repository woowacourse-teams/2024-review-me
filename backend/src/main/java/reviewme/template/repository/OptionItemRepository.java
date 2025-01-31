package reviewme.template.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    @Query("""
            SELECT o FROM OptionGroup og
            JOIN og.optionItems o
            WHERE og.id = :optionGroupId
            """)
    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    @Query("""
            SELECT o FROM OptionItem o
            WHERE o.optionType = :optionType
            """)
    List<OptionItem> findAllByOptionType(OptionType optionType);
}
