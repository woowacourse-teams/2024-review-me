package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
}
