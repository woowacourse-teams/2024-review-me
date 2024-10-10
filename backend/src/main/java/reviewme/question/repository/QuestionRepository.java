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

    @Query(value = """
            SELECT q.id FROM question q
            JOIN section_question sq
            ON q.id = sq.question_id
            JOIN template_section ts
            ON sq.section_id = ts.section_id
            WHERE ts.template_id = :templateId
            """, nativeQuery = true)
    Set<Long> findAllQuestionIdByTemplateId(long templateId);

    @Query(value = """
            SELECT q.* FROM question q
            JOIN section_question sq
            ON q.id = sq.question_id
            JOIN template_section ts
            ON sq.section_id = ts.section_id
            WHERE ts.template_id = :templateId
            """, nativeQuery = true)
    List<Question> findAllByTemplatedId(long templateId);

    @Query(value = """
            SELECT q FROM Question q
            JOIN SectionQuestion sq ON q.id = sq.questionId
            JOIN TemplateSection ts ON sq.sectionId = ts.sectionId
            WHERE sq.sectionId = :sectionId
            """)
    List<Question> findAllBySectionId(long sectionId);

    @Query("""
            SELECT o FROM OptionItem o
            JOIN OptionGroup og ON o.optionGroupId = og.id
            WHERE og.questionId = :questionId
            """)
    List<OptionItem> findAllOptionItemsById(long questionId);
}
