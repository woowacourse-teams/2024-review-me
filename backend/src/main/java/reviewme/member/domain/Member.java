package reviewme.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private GithubId githubId;

    public Member(String name, long githubId) {
        this.name = name;
        this.githubId = new GithubId(githubId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member member)) {
            return false;
        }
        if (id == null) {
            return Objects.equals(githubId, member.githubId);
        }
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return Objects.hash(githubId);
        }
        return Objects.hash(id);
    }
}
