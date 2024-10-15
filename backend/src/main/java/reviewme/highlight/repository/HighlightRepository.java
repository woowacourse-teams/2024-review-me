package reviewme.highlight.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import reviewme.highlight.entity.Highlight;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {

    @Modifying
    @Query("""
            DELETE FROM Highlight h
            WHERE h.answerId IN :answerIds
            """)
    void deleteAllByAnswerIds(Collection<Long> answerIds);
}
