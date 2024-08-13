package reviewme.review.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.question.domain.Question;
import reviewme.question.domain.exception.QuestionNotFoundException;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = """
            SELECT q.* FROM question q
            LEFT JOIN section_question sq
            ON sq.question_id = q.id
            WHERE sq.section_id = :sectionId
            ORDER BY q.position ASC
            """, nativeQuery = true)
    List<Question> findAllBySectionId(long sectionId);

    @Query(value = """
            SELECT q.* FROM question q
            LEFT JOIN section_question sq
            ON sq.question_id = q.id
            LEFT JOIN template_section ts
            ON sq.section_id = ts.section_id
            WHERE ts.template_id = :templateId
            """, nativeQuery = true)
    Set<Long> findAllQuestionIdByTemplateId(long templateId);

    default Question getQuestionById(long id) {
        return findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
