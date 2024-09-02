package reviewme.fixture;

import org.springframework.stereotype.Component;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
public class ReviewGroupFixture {

    public static ReviewGroup 리뷰_그룹() {
        return 리뷰_그룹("reviewRequestCode", "groupAccessCode");
    }

    public static ReviewGroup 리뷰_그룹(String reviewRequestCode, String groupAccessCode) {
        return new ReviewGroup("revieweeName", "projectName", reviewRequestCode, groupAccessCode);
    }
}
