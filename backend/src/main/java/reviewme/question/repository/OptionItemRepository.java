package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;

@Repository
public interface OptionItemRepository extends JpaRepository<OptionItem, Long> {

    List<OptionItem> findAllByOptionGroupId(long optionGroupId);

    @Query(value = """
            SELECT o.id FROM option_item o
            LEFT JOIN checkbox_answer_selected_option c
            ON c.selected_option_id = o.id
            LEFT JOIN checkbox_answer ca
            ON c.checkbox_answer_id = ca.id
            WHERE ca.review_id = :reviewId
            """, nativeQuery = true)
    Set<Long> findSelectedOptionItemIdsByReviewId(long reviewId);

    @Query(value = """
            SELECT o.* FROM option_item o
            LEFT JOIN checkbox_answer_selected_option c
            ON c.selected_option_id = o.id
            LEFT JOIN checkbox_answer ca
            ON c.checkbox_answer_id = ca.id
            WHERE ca.review_id = :reviewId
            AND ca.question_id = :questionId
            ORDER BY o.position ASC
            """, nativeQuery = true)
    List<OptionItem> findSelectedOptionItemsByReviewIdAndQuestionId(long reviewId, long questionId);

    @Query(value = """
            SELECT o.* FROM option_item o
            INNER JOIN checkbox_answer_selected_option cao
            ON cao.selected_option_id = o.id
            INNER JOIN checkbox_answer ca
            ON cao.checkbox_answer_id = ca.id
            WHERE ca.review_id = :reviewId
            AND o.option_type = :#{#optionType.name()}
            """, nativeQuery = true)
    List<OptionItem> findByReviewIdAndOptionType(long reviewId, OptionType optionType);
}
