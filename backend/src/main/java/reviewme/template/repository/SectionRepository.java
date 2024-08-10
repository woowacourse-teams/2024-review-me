package reviewme.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;
import reviewme.template.domain.exception.SectionNotFoundException;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    default Section getSectionById(long id) {
        return findById(id).orElseThrow(() -> new SectionNotFoundException(id));
    }
}
