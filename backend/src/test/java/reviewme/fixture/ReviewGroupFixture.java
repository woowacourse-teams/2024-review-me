package reviewme.fixture;

import reviewme.reviewgroup.domain.ReviewGroup;

public class ReviewGroupFixture {

    public static ReviewGroup 리뷰_그룹(String reviewRequestCode, String groupAccessCode) {
        return new ReviewGroup(1L, "revieweeName", "projectName", reviewRequestCode, groupAccessCode);
    }

    public static ReviewGroup 템플릿_지정_리뷰_그룹(long templateId) {
        return new ReviewGroup(templateId, "reviewee", "project", "requestCode", "accessCode");
    }

    public static ReviewGroup 리뷰_그룹() {
        return 리뷰_그룹("reviewRequestCode", "groupAccessCode");
    }
}
