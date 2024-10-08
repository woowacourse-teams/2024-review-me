package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 잘못된_리뷰_요청_코드로_리뷰_작성폼을_요청할_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when, then
        assertThatThrownBy(() -> templateService.generateReviewForm(reviewGroup.getReviewRequestCode() + " "))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
