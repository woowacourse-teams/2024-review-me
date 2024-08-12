package reviewme.review.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.question.domain.Question2;
import reviewme.question.domain.exception.QuestionNotFoundException;

public interface QuestionRepository2 extends JpaRepository<Question2, Long> {

    @Query(value = """
            SELECT q.* FROM question2 q
            LEFT JOIN section_question sq
            ON sq.question_id = q.id
            WHERE sq.section_id = :sectionId
            ORDER BY q.position ASC
            """, nativeQuery = true)
    List<Question2> findAllBySectionId(long sectionId);

    @Query(value = """
            SELECT q.* FROM question2 q
            LEFT JOIN section_question sq
            ON sq.question_id = q.id
            LEFT JOIN template_section ts
            ON sq.section_id = ts.section_id
            WHERE ts.template_id = :templateId
            """, nativeQuery = true)
    Set<Long> findAllQuestionIdByTemplateId(Long templateId);

    default Question2 getQuestionById(long id) {
        return findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
