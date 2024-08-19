package reviewme.reviewgroup.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Repository
public interface ReviewGroupRepository extends JpaRepository<ReviewGroup, Long> {

    Optional<ReviewGroup> findByReviewRequestCode(String reviewRequestCode);

    @Query(value = """
            SELECT rg.* FROM review_group rg
            WHERE rg.review_request_code = :reviewRequestCode
            AND rg.group_access_code = :groupAccessCode
            """, nativeQuery = true
    )
    Optional<ReviewGroup> findByReviewRequestCodeAndGroupAccessCode(String reviewRequestCode, String groupAccessCode);

    boolean existsByReviewRequestCode(String reviewRequestCode);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1 FROM review_group rg
                WHERE rg.review_request_code = :reviewRequestCode
                AND rg.group_access_code = :groupAccessCode
            )
            """, nativeQuery = true
    )
    boolean existsByReviewRequestCodeAndGroupAccessCode(String reviewRequestCode, String groupAccessCode);
}
