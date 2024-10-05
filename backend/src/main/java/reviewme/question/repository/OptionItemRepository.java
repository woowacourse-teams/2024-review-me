package reviewme.question.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    @Query(value = """
            SELECT o.* FROM option_item o
            WHERE o.option_type = :#{#optionType.name()}
            """, nativeQuery = true)
    List<OptionItem> findAllByOptionType(OptionType optionType);

    @Query(value = """
            SELECT o.* FROM option_item o
            JOIN option_group og
            ON o.option_group_id = og.id
            WHERE og.question_id IN (:questionIds)
            """, nativeQuery = true)
    List<OptionItem> findAllByQuestionIds(List<Long> questionIds);

    @Query(value = """
            SELECT o.* FROM option_item o 
            JOIN checkbox_answer_selected_option caso on caso.selected_option_id = o.id  
            JOIN checkbox_answer ca ON ca.id = caso.checkbox_answer_id  
            WHERE ca.review_id = :reviewId 
            AND o.option_type = :#{#optionType.name()} 
            """, nativeQuery = true)
    List<OptionItem> findByReviewIdAndOptionType(long reviewId, OptionType optionType);
}
