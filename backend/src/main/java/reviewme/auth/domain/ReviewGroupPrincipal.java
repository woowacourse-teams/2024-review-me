package reviewme.auth.domain;

import lombok.Getter;
import reviewme.reviewgroup.domain.ReviewGroup;

@Getter
public class ReviewGroupPrincipal extends Principal {

    private final ReviewGroup reviewGroup;

    public ReviewGroupPrincipal(ReviewGroup reviewGroup) {
        this.reviewGroup = reviewGroup;
    }
}
