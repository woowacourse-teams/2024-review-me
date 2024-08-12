package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.question.domain.Question2;

public interface QuestionRepository2 extends JpaRepository<Question2, Long> {

    @Query(value = """
            SELECT q.* FROM question2 q
            LEFT JOIN section_question sq
            ON sq.question_id = q.id
            WHERE sq.section_id = :sectionId
            ORDER BY q.position ASC
            """, nativeQuery = true)
    List<Question2> findAllBySectionId(long sectionId);
}
