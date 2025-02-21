package reviewme.template.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("""
            SELECT s FROM Template t
            JOIN t.sections s
            WHERE t.id = :templateId
            ORDER BY s.position ASC
            """)
    List<Section> findAllByTemplateId(long templateId);

    @Query("""
            SELECT s FROM Template t
            JOIN t.sections s
            WHERE s.id = :sectionId
            AND t.id = :templateId
            """)
    Optional<Section> findByIdAndTemplateId(long sectionId, long templateId);
}
