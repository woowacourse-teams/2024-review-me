package reviewme.member.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reviewme.member.domain.exception.DuplicateReviewerException;
import reviewme.member.domain.exception.EmptyReviewerException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewerGroupGithubIds {

    @OneToMany(mappedBy = "reviewerGroup")
    private Set<GithubIdReviewerGroup> reviewerGithubIds;

    public ReviewerGroupGithubIds(ReviewerGroup reviewerGroup, List<GithubId> githubIds) {
        if (githubIds.isEmpty()) {
            throw new EmptyReviewerException();
        }
        Set<GithubIdReviewerGroup> reviewers = githubIds.stream()
                .map(githubId -> new GithubIdReviewerGroup(githubId, reviewerGroup))
                .collect(Collectors.toSet());
        if (reviewers.size() != githubIds.size()) {
            throw new DuplicateReviewerException();
        }
        this.reviewerGithubIds = reviewers;
    }

    public void add(GithubIdReviewerGroup githubIdReviewerGroup) {
        if (reviewerGithubIds.contains(githubIdReviewerGroup)) {
            throw new DuplicateReviewerException();
        }
        reviewerGithubIds.add(githubIdReviewerGroup);
    }

    public boolean doesNotContain(Member reviewer) {
        GithubId githubId = reviewer.getGithubId();
        return reviewerGithubIds.stream()
                .map(GithubIdReviewerGroup::getGithubId)
                .noneMatch(githubId::equals);
    }
}
