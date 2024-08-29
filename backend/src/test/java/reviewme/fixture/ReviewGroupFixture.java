package reviewme.fixture;

import reviewme.reviewgroup.domain.ReviewGroup;

public class ReviewGroupFixture {

    public ReviewGroup 리뷰_그룹() {
        return this.리뷰_그룹("reviewRequestCode", "groupAccessCode");
    }

    public ReviewGroup 리뷰_그룹(String reviewRequestCode, String groupAccessCode) {
        return new ReviewGroup("revieweeName", "projectName", reviewRequestCode, groupAccessCode);
    }
}