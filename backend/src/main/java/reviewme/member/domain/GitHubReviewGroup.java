package reviewme.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "git_hub_review_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GitHubReviewGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gitHubId;

    @ManyToOne
    @JoinColumn(name = "reviewer_group")
    private ReviewerGroup reviewerGroup;

    public GitHubReviewGroup(String gitHubId, ReviewerGroup reviewerGroup) {
        this.gitHubId = gitHubId;
        this.reviewerGroup = reviewerGroup;
    }
}
