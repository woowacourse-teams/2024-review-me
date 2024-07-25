package reviewme.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Getter
public class GithubIdReviewerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GithubId githubId;

    @ManyToOne
    private ReviewerGroup reviewerGroup;

    public GithubIdReviewerGroup(GithubId githubId, ReviewerGroup reviewerGroup) {
        this.githubId = githubId;
        this.reviewerGroup = reviewerGroup;
    }
}
