package reviewme.template.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.template.domain.Section;
import reviewme.template.domain.exception.SectionNotFoundException;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findById(long id);

    default Section getSectionById(long id) {
        return findById(id).orElseThrow(() -> new SectionNotFoundException(id));
    }
}
