package reviewme.member;

import lombok.Getter;

@Getter
public class GithubId {

    private long id;

    public GithubId(long id) {
        this.id = id;
    }
}
