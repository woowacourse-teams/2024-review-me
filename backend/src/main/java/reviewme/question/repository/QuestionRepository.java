package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
            SELECT q.id FROM Question q
            JOIN SectionQuestion sq
            ON q.id = sq.questionId
            JOIN TemplateSection ts
            ON sq.sectionId = ts.sectionId
            WHERE ts.templateId = :templateId
            """)
    Set<Long> findAllQuestionIdByTemplateId(long templateId);

    @Query("""
            SELECT q FROM Question q
            JOIN SectionQuestion sq
            ON q.id = sq.questionId
            JOIN TemplateSection ts
            ON sq.sectionId = ts.sectionId
            WHERE ts.templateId = :templateId
            """)
    List<Question> findAllByTemplatedId(long templateId);

    @Query("""
            SELECT q FROM Question q
            JOIN SectionQuestion sq ON q.id = sq.questionId
            WHERE sq.sectionId = :sectionId
            """)
    List<Question> findAllBySectionId(long sectionId);

    @Query("""
            SELECT o FROM OptionItem o
            JOIN OptionGroup og ON o.optionGroupId = og.id
            WHERE og.questionId = :questionId
            ORDER BY o.position
            """)
    List<OptionItem> findAllOptionItemsByIdOrderByPosition(long questionId);
}
