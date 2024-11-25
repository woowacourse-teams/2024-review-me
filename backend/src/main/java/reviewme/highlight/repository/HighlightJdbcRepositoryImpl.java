package reviewme.highlight.repository;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import reviewme.highlight.domain.Highlight;

@RequiredArgsConstructor
public class HighlightJdbcRepositoryImpl implements HighlightJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void saveAll(Collection<Highlight> highlights) {
        SqlParameterSource[] parameterSources = SqlParameterSourceUtils.createBatch(highlights.toArray());
        String insertSql = """
                INSERT INTO highlight (answer_id, line_index, start_index, end_index)
                VALUES (:answerId, :lineIndex, :highlightRange.startIndex, :highlightRange.endIndex)
                """;
        namedParameterJdbcTemplate.batchUpdate(insertSql, parameterSources);
    }
}
