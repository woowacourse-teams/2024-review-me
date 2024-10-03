package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = """
            SELECT q.* FROM question q
            JOIN section_question qa
            ON q.id = sq.question_id
            WHERE sq.section_id = :sectionId
            """, nativeQuery = true)
    List<Question> findAllBySectionId(long sectionId);

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
}
