package reviewme.question.repository;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    @Cacheable(value = "templateCache", key = "#optionGroupId")
    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    @Cacheable(value = "templateCache", key = "#optionType")
    @Query("""
            SELECT o FROM OptionItem o
            WHERE o.optionType = :optionType
            """)
    List<OptionItem> findAllByOptionType(OptionType optionType);

    @Cacheable(value = "templateCache", key = "#questionId")
    @Query("""
            SELECT o FROM OptionItem o
            JOIN OptionGroup og ON o.optionGroupId = og.id
            WHERE og.questionId = :questionId
            ORDER BY o.position
            """)
    List<OptionItem> findAllOptionItemsByIdOrderByPosition(long questionId);

    @Cacheable(value = "templateCache", key = "#questionId")
    @Query("""
            SELECT o FROM OptionItem o
            JOIN OptionGroup og ON o.optionGroupId = og.id
            WHERE og.questionId = :questionId
            """)
    List<OptionItem> findByQuestionId(long questionId);
}
