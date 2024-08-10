package reviewme.template.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.template.domain.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = "" +
            "SELECT * FROM section s LEFT JOIN template_section ts " +
            "ON ts.section_id = s.id " +
            "WHERE ts.template_id = :templateId " +
            "ORDER BY s.position ASC",
            nativeQuery = true
    )
    List<Section> findAllByTemplateId(long templateId);
}
