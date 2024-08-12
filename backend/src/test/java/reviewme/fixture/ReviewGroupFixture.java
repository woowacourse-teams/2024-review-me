package reviewme.fixture;

import reviewme.reviewgroup.domain.ReviewGroup;

public class ReviewGroupFixture {

    public static final ReviewGroup 리뷰그룹 = new ReviewGroup(
            "reviewName",
            "projectName",
            "reviewRequestCode",
            "groupAccessCode"
    );
}
