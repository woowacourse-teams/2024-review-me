package reviewme.highlight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import reviewme.highlight.domain.HighLight;

public interface HighlightRepository extends JpaRepository<HighLight, Long> {

    @Modifying
    @Query(value = """
            DELETE FROM HighLight h
            WHERE h.reviewGroupId = :reviewGroupId
            AND h.questionId = :questionId
            """)
    void deleteByReviewGroupIdAndQuestionId(long reviewGroupId, long questionId);
}
