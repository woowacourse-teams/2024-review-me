package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.exception.OptionItemNotFoundException;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    List<OptionItem> findAllByOptionType(OptionType optionType);

    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    boolean existsByOptionTypeAndId(OptionType optionType, long id);

    @Query(value = """
            SELECT o.id FROM option_item o
            LEFT JOIN checkbox_answer ca
            LEFT JOIN checkbox_answer_selected_option c
            ON c.checkbox_answer_id = ca.id
            WHERE ca.review_id = :reviewId
            AND c.selected_option_id = o.id
            """, nativeQuery = true)
    Set<Long> findSelectedOptionItemIdsByReviewId(long reviewId);

    @Query(value = """
            SELECT o.* FROM option_item o
            LEFT JOIN checkbox_answer ca
            LEFT JOIN checkbox_answer_selected_option c
            ON c.checkbox_answer_id = ca.id
            WHERE ca.review_id = :reviewId
            AND ca.question_id = :questionId
            AND c.selected_option_id = o.id
            ORDER BY o.position ASC
            """, nativeQuery = true)
    List<OptionItem> findSelectedOptionItemsByReviewIdAndQuestionId(long reviewId, long questionId);

    default OptionItem getOptionItemById(long id) {
        return findById(id).orElseThrow(() -> new OptionItemNotFoundException(id));
    }
}
