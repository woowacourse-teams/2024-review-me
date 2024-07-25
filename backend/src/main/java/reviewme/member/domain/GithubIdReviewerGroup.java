package reviewme.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "github_id_reviewer_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GithubIdReviewerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GithubId githubId;

    @ManyToOne
    @JoinColumn(name = "reviewer_group_id", nullable = false)
    private ReviewerGroup reviewerGroup;

    public GithubIdReviewerGroup(GithubId githubId, ReviewerGroup reviewerGroup) {
        this.githubId = githubId;
        this.reviewerGroup = reviewerGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GithubIdReviewerGroup githubIdReviewerGroup)) {
            return false;
        }
        if (id == null) {
            return Objects.equals(githubId, githubIdReviewerGroup.githubId);
        }

        return Objects.equals(id, githubIdReviewerGroup.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return Objects.hash(githubId);
        }
        return Objects.hash(id);
    }
}
