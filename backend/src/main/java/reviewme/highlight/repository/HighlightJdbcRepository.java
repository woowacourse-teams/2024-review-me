package reviewme.highlight.repository;

import java.util.Collection;
import reviewme.highlight.domain.Highlight;

public interface HighlightJdbcRepository {

    void saveAll(Collection<Highlight> highlights);
}
