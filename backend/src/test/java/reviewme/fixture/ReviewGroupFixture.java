package reviewme.fixture;

import reviewme.reviewgroup.domain.ReviewGroup;

public enum ReviewGroupFixture {

    리뷰_그룹("reviewName", "projectName", "reviewRequestCode", "groupAccessCode"),
    ;

    private String reviewName;
    private String projectName;
    private String reviewRequestCode;
    private String groupAccessCode;

    ReviewGroupFixture(String reviewName, String projectName, String reviewRequestCode, String groupAccessCode) {
        this.reviewName = reviewName;
        this.projectName = projectName;
        this.reviewRequestCode = reviewRequestCode;
        this.groupAccessCode = groupAccessCode;
    }

    public ReviewGroup create() {
        return new ReviewGroup(reviewName, projectName, reviewRequestCode, groupAccessCode);
    }
}
