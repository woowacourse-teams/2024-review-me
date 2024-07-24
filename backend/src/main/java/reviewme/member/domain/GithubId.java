package reviewme.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class GithubId {

    @Column(name = "github_id", nullable = false)
    private long id;

    public GithubId(long id) {
        this.id = id;
    }
}
