package reviewme.question.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Cacheable(value = "templateCache", key = "#templateId")
    @Query("""
            SELECT q.id FROM Question q
            JOIN SectionQuestion sq
            ON q.id = sq.questionId
            JOIN TemplateSection ts
            ON sq.sectionId = ts.sectionId
            WHERE ts.templateId = :templateId
            """)
    Set<Long> findAllQuestionIdByTemplateId(long templateId);

    @Cacheable(value = "templateCache", key = "#templateId")
    @Query("""
            SELECT q FROM Question q
            JOIN SectionQuestion sq
            ON q.id = sq.questionId
            JOIN TemplateSection ts
            ON sq.sectionId = ts.sectionId
            WHERE ts.templateId = :templateId
            """)
    List<Question> findAllByTemplatedId(long templateId);

    @Cacheable(value = "templateCache", key = "#sectionId")
    @Query("""
            SELECT q FROM Question q
            JOIN SectionQuestion sq ON q.id = sq.questionId
            WHERE sq.sectionId = :sectionId
            ORDER BY q.position
            """)
    List<Question> findAllBySectionIdOrderByPosition(long sectionId);

    @Cacheable(value = "templateCache", key = "#questionIds.hashCode()")
    @Query("""
        SELECT q FROM Question q
        WHERE q.id IN :questionIds
        """)
    Collection<Question> findAllById(Collection<Long> questionIds);

    @Cacheable(value = "templateCache", key = "#questionId")
    Optional<Question> findById(long questionId);
}
