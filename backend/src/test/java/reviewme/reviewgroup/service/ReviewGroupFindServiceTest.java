package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewGroupFindServiceTest {

    @Autowired
    ReviewGroupFindService reviewGroupFindService;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰_요청_코드로_리뷰_그룹을_조회한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup(
                "ted",
                "review-me",
                "reviewRequestCode",
                "groupAccessCode"
        ));

        // when
        ReviewGroupResponse response = reviewGroupFindService.findReviewGroup(reviewGroup.getReviewRequestCode());

        // then
        assertAll(
                () -> assertThat(response.revieweeName()).isEqualTo(reviewGroup.getReviewee()),
                () -> assertThat(response.projectName()).isEqualTo(reviewGroup.getProjectName())
        );
    }
}
