package reviewme.question.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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
            SELECT q.* FROM question q
            JOIN section_question sq 
            ON q.id = sq.question_id
            JOIN template_section ts 
            ON sq.section_id = ts.section_id
            JOIN review_group rg 
            ON rg.template_id = ts.template_id
            WHERE sq.section_id = :sectionId AND rg.review_request_code =:reviewRequestCode
            """, nativeQuery = true)
    List<Question> findAllByReviewRequestCodeAndSectionId(String reviewRequestCode, long sectionId);
}
