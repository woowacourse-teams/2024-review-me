package reviewme.highlight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.highlight.domain.HighLight;

public interface HighlightRepository extends JpaRepository<HighLight, Long> {
}
