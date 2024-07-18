package reviewme.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.GitHubReviewGroup;
import reviewme.member.domain.ReviewerGroup;

@Repository
public interface GitHubReviewGroupRepository extends JpaRepository<GitHubReviewGroup, Long> {

    boolean existsByGitHubIdAndReviewerGroup(String gitHubId, ReviewerGroup reviewerGroup);
}
