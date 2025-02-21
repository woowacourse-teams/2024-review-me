package reviewme.template.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
            SELECT q.id FROM Template t
            JOIN t.sections s
            JOIN s.questions q
            WHERE t.id = :templateId
            """)
    Set<Long> findAllQuestionIdByTemplateId(long templateId);

    @Query("""
            SELECT q FROM Section s
            Join s.questions q
            WHERE s.id = :sectionId
            ORDER BY q.position
            """)
    List<Question> findAllBySectionIdOrderByPosition(long sectionId);

    @Query("""
            SELECT o FROM Question q
            JOIN q.optionGroup og
            JOIN og.optionItems o
            WHERE q.id = :questionId
            ORDER BY o.position
            """)
    List<OptionItem> findAllOptionItemsByIdOrderByPosition(long questionId);
}
