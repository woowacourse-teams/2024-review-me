package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.question.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = """
            SELECT q.* FROM question q
            JOIN section_question sq
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

    @Query(value = """
            SELECT q.* FROM question q 
            join section_question sq 
            on q.id = sq.question_id 
            LEFT JOIN text_answer ta 
            ON q.id = ta.question_id 
            LEFT JOIN checkbox_answer ca 
            ON q.id = ca.question_id 
            WHERE (ta.review_id = :reviewId 
            OR ca.review_id = :reviewId) 
            and sq.section_id = :sectionId
            """, nativeQuery = true)
    Set<Question> findByReviewIdAndSectionId(long reviewId, long sectionId);
}
