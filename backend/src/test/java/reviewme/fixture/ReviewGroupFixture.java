package reviewme.fixture;

import java.util.UUID;
import reviewme.reviewgroup.domain.ReviewGroup;

public class ReviewGroupFixture {

    public static ReviewGroup 비회원_리뷰_그룹(String reviewRequestCode, String groupAccessCode) {
        return new ReviewGroup(1L, "revieweeName", "projectName", reviewRequestCode, groupAccessCode);
    }

    public static ReviewGroup 템플릿_지정_비회원_리뷰_그룹(long templateId) {
        return new ReviewGroup(templateId, "reviewee", "project", generateRandomString(), "accessCode");
    }

    public static ReviewGroup 비회원_리뷰_그룹() {
        return 비회원_리뷰_그룹(generateRandomString(), "groupAccessCode");
    }

    public static ReviewGroup 회원_리뷰_그룹() {
        return new ReviewGroup(1L, 1L, "reviewee", "project", generateRandomString());
    }

    public static ReviewGroup 회원_지정_리뷰_그룹(long memberId) {
        return new ReviewGroup(memberId, 1L, "reviewee", "project", generateRandomString());
    }

    private static String generateRandomString() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
