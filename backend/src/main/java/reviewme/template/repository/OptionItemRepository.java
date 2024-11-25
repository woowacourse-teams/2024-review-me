package reviewme.template.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    @Query("""
            SELECT o FROM OptionItem o
            WHERE o.optionType = :optionType
            """)
    List<OptionItem> findAllByOptionType(OptionType optionType);

    @Query("""
            SELECT o FROM OptionItem o
            JOIN OptionGroup og
            ON o.optionGroupId = og.id
            WHERE og.questionId IN :questionIds
            """)
    List<OptionItem> findAllByQuestionIds(List<Long> questionIds);
}
