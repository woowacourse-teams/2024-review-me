package reviewme.member.domain;

import jakarta.persistence.Column;
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
@Table(name = "github_reviewer_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GithubReviewerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false)
    private String githubId;

    @ManyToOne
    @JoinColumn(name = "reviewer_group_id")
    private ReviewerGroup reviewerGroup;

    public GithubReviewerGroup(String githubId, ReviewerGroup reviewerGroup) {
        this.githubId = githubId;
        this.reviewerGroup = reviewerGroup;
    }
}
