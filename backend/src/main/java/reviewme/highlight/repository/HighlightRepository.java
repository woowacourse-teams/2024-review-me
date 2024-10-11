package reviewme.highlight.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import reviewme.highlight.domain.Highlight;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {

    @Modifying
    @Query("""
            DELETE FROM Highlight h
            WHERE h.answerId IN :answersByReviewQuestion
            """)
    void deleteAllByIds(List<Long> answersByReviewQuestion);
}
