package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewGroupLookupServiceTest {

    @Autowired
    ReviewGroupLookupService reviewGroupLookupService;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰_요청_코드로_리뷰_그룹을_조회한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup(
                "ted",
                "review-me",
                "reviewRequestCode",
                "groupAccessCode",
                1L
        ));

        // when
        ReviewGroupResponse response = reviewGroupLookupService.getReviewGroupSummary(
                reviewGroup.getReviewRequestCode()
        );

        // then
        assertAll(
                () -> assertThat(response.revieweeName()).isEqualTo(reviewGroup.getReviewee()),
                () -> assertThat(response.projectName()).isEqualTo(reviewGroup.getProjectName())
        );
    }

    @Test
    void 리뷰_요청_코드에_대한_리뷰_그룹이_존재하지_않을_경우_예외가_발생한다() {
        // given, when, then
        assertThatThrownBy(() -> reviewGroupLookupService.getReviewGroupSummary("reviewRequestCode"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
