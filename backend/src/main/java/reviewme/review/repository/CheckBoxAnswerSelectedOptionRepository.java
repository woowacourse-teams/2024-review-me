package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;

public interface CheckBoxAnswerSelectedOptionRepository extends JpaRepository<CheckBoxAnswerSelectedOption, Long> {
    @Query(value = """
            SELECT caso.* FROM checkbox_answer_selected_option caso
            WHERE caso.checkbox_answer_id IN (:checkboxAnswerIds)
            """, nativeQuery = true)
    List<CheckBoxAnswerSelectedOption> findAllByCheckboxAnswerIds(List<Long> checkboxAnswerIds);
}
