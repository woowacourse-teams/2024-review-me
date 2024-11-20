package reviewme.template.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @EntityGraph(attributePaths = {"questionIds"})
    @Query("""
            SELECT s FROM Section s
            JOIN TemplateSection ts ON s.id = ts.sectionId
            WHERE ts.templateId = :templateId
            ORDER BY s.position ASC
            """)
    List<Section> findAllByTemplateId(long templateId);

    @EntityGraph(attributePaths = {"questionIds"})
    @Query("""
            SELECT s FROM Section s
            JOIN TemplateSection ts ON s.id = ts.sectionId
            WHERE ts.sectionId = :sectionId
            AND ts.templateId = :templateId
            """)
    Optional<Section> findByIdAndTemplateId(long sectionId, long templateId);
}
