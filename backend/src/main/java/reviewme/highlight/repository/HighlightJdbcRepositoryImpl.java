package reviewme.highlight.repository;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reviewme.highlight.domain.Highlight;

@Repository
@RequiredArgsConstructor
public class HighlightJdbcRepositoryImpl implements HighlightJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(Collection<Highlight> highlights) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO highlight (answer_id, line_index, start_index, end_index) VALUES (?, ?, ?, ?)",
                highlights,
                highlights.size(),
                (ps, highlight) -> {
                    ps.setLong(1, highlight.getAnswerId());
                    ps.setInt(2, highlight.getLineIndex());
                    ps.setInt(3, highlight.getHighlightRange().getStartIndex());
                    ps.setInt(4, highlight.getHighlightRange().getEndIndex());
                });
    }
}
