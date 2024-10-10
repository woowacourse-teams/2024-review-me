package reviewme.template.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = """
            SELECT s.* FROM section s
            JOIN template_section ts
            ON s.id = ts.section_id
            WHERE ts.template_id = :templateId
            ORDER BY s.position ASC
            """, nativeQuery = true)
    List<Section> findAllByTemplateId(long templateId);

    @Query("""
            SELECT s FROM Section s
            JOIN TemplateSection ts ON s.id = ts.sectionId
            WHERE ts.sectionId = :sectionId
            AND ts.templateId = :templateId
            """)
    boolean existsByIdAndTemplateId(long sectionId, long templateId);
}
