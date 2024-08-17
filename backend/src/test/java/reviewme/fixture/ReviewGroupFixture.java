package reviewme.fixture;

import reviewme.reviewgroup.domain.ReviewGroup;

public enum ReviewGroupFixture {

    리뷰_그룹("reviewName", "projectName", "reviewRequestCode", "groupAccessCode"),
    ;

    private final String reviewName;
    private final String projectName;
    private final String reviewRequestCode;
    private final String groupAccessCode;

    ReviewGroupFixture(String reviewName, String projectName, String reviewRequestCode, String groupAccessCode) {
        this.reviewName = reviewName;
        this.projectName = projectName;
        this.reviewRequestCode = reviewRequestCode;
        this.groupAccessCode = groupAccessCode;
    }

    public ReviewGroup create() {
        return new ReviewGroup(this.reviewName, this.projectName, this.reviewRequestCode, this.groupAccessCode);
    }

    public ReviewGroup createWithGroupAccessCode(String groupAccessCode) {
        return new ReviewGroup(this.reviewName, this.projectName, this.reviewRequestCode, groupAccessCode);
    }
}
