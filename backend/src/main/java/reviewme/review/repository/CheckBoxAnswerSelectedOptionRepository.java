package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;

public interface CheckBoxAnswerSelectedOptionRepository extends JpaRepository<CheckBoxAnswerSelectedOption, Long> {

    @Query(value = """
            select caso.selected_option_id from checkbox_answer_selected_option caso 
            join checkbox_answer ca 
            on ca.id = caso.checkbox_answer_id
            where ca.review_id = :reviewId 
            and ca.question_id = :questionId 
            """, nativeQuery = true)
    List<Long> findSelectedOptionIdByReviewAndQuestion(long reviewId, long questionId);
}
