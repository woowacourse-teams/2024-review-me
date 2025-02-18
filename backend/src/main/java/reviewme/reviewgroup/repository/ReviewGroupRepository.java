package reviewme.reviewgroup.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse;

@Repository
public interface ReviewGroupRepository extends JpaRepository<ReviewGroup, Long> {

    Optional<ReviewGroup> findByReviewRequestCode(String reviewRequestCode);

    @Query("""
            SELECT rg FROM ReviewGroup rg
            WHERE rg.id IN :reviewGroupIds
            """)
    List<ReviewGroup> findAllByIds(Set<Long> reviewGroupIds);

    @Query("""
             SELECT new reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse(
                rg.id, rg.reviewee, rg.projectName, rg.reviewRequestCode, rg.createdAt, COUNT(r.id)
             )
             FROM ReviewGroup rg
             LEFT JOIN Review r ON rg.id = r.reviewGroupId
             WHERE rg.memberId = :memberId
             AND (:lastReviewGroupId IS NULL OR rg.id < :lastReviewGroupId)
             GROUP BY rg.id
             ORDER BY rg.createdAt DESC, rg.id DESC
             LIMIT :limit
            """)
    List<ReviewGroupPageElementResponse> findAllByMemberIdWithLimit(long memberId, Long lastReviewGroupId, int limit);

    boolean existsByReviewRequestCode(String reviewRequestCode);

    boolean existsByIdAndReviewRequestCode(long id, String reviewRequestCode);

    boolean existsByIdAndMemberId(long id, long memberId);
}
