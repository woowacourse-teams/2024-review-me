package reviewme.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.GithubReviewerGroup;
import reviewme.member.domain.ReviewerGroup;

@Repository
public interface GithubReviewerGroupRepository extends JpaRepository<GithubReviewerGroup, Long> {

    boolean existsByGithubIdAndReviewerGroup(String githubId, ReviewerGroup reviewerGroup);
}
