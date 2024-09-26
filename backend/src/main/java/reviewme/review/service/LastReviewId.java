package reviewme.review.service;

import lombok.Getter;

@Getter
public class LastReviewId {

    private static final long DEFAULT_LAST_REVIEW_ID = Long.MAX_VALUE;

    private final long id;

    LastReviewId(Long id) {
        if (id == null) {
            this.id = DEFAULT_LAST_REVIEW_ID;
            return;
        }
        this.id = id;
    }
}
