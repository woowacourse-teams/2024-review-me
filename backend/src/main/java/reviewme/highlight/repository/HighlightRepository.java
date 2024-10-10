package reviewme.highlight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.highlight.domain.Highlight;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {
}
