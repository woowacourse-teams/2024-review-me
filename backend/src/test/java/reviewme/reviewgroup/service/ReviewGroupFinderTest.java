package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.exception.ReviewGroupUnAuthorizedException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewGroupFinderTest {

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewGroupFinder reviewGroupFinder;

    @Autowired
    private GroupAccessCodeEncoder groupAccessCodeEncoder;

    @Test
    void 리뷰_요청_코드로_리뷰_그룹을_조회한다() {
        // given
        String reviewRequestCode = "1234";
        reviewGroupRepository.save(new ReviewGroup("아루", "리뷰미", reviewRequestCode, "5678"));

        // when, then
        assertDoesNotThrow(() -> reviewGroupFinder.getByReviewRequestCode(reviewRequestCode));
    }

    @Test
    void 리뷰_요청_코드가_존재하지_않으면_예외를_발생한다() {
        assertThatThrownBy(() -> reviewGroupFinder.getByReviewRequestCode("0000"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 리뷰_요청_코드와_그룹_액세스_코드로_리뷰_그룹을_조회한다() {
        // given
        String reviewRequestCode = "1234";
        String groupAccessCode = "5678";
        String encodedGroupAccessCode = groupAccessCodeEncoder.encode(groupAccessCode);
        reviewGroupRepository.save(new ReviewGroup("아루", "리뷰미", reviewRequestCode, encodedGroupAccessCode));

        // when, then
        assertDoesNotThrow(() -> reviewGroupFinder.getByCodes(reviewRequestCode, groupAccessCode));
    }

    @Test
    void 그룹_액세스_코드가_일치하지_않으면_예외를_발생한다() {
        // given
        String reviewRequestCode = "1234";
        String groupAccessCode = "5678";
        reviewGroupRepository.save(new ReviewGroup("아루", "리뷰미", reviewRequestCode, groupAccessCode));

        // when, then
        assertThatThrownBy(() -> reviewGroupFinder.getByCodes(reviewRequestCode, "0000"))
                .isInstanceOf(ReviewGroupUnAuthorizedException.class);
    }
}
