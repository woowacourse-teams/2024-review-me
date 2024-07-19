package reviewme.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.GithubReviewGroup;
import reviewme.member.domain.ReviewerGroup;

@Repository
public interface GithubReviewGroupRepository extends JpaRepository<GithubReviewGroup, Long> {

    boolean existsByGithubIdAndReviewerGroup(String githubId, ReviewerGroup reviewerGroup);
}
