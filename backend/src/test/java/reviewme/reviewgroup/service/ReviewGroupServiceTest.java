package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.support.ServiceTest;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
@ExtendWith(MockitoExtension.class)
class ReviewGroupServiceTest {

    @MockBean
    private RandomCodeGenerator randomCodeGenerator;

    @Autowired
    private ReviewGroupService reviewGroupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 코드가_중복되는_경우_다시_생성한다() {
        // given
        templateRepository.save(템플릿(List.of()));
        reviewGroupRepository.save(리뷰_그룹("0000", "1111"));
        given(randomCodeGenerator.generate(anyInt()))
                .willReturn("0000") // ReviewRequestCode
                .willReturn("AAAA");

        ReviewGroupCreationRequest request = new ReviewGroupCreationRequest("sancho", "reviewme", "groupAccessCode");

        // when
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);

        // then
        assertThat(response).isEqualTo(new ReviewGroupCreationResponse("AAAA"));
        then(randomCodeGenerator).should(times(2)).generate(anyInt());
    }

    @Test
    void 리뷰_요청_코드와_리뷰_확인_코드가_일치하는지_확인한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        String groupAccessCode = "groupAccessCode";
        reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

        CheckValidAccessRequest request = new CheckValidAccessRequest(reviewRequestCode, groupAccessCode);
        CheckValidAccessRequest wrongRequest = new CheckValidAccessRequest(reviewRequestCode, groupAccessCode + "!");

        // when
        assertAll(
                () -> assertDoesNotThrow(() -> reviewGroupService.checkGroupAccessCode(request)),
                () -> assertThatThrownBy(() -> reviewGroupService.checkGroupAccessCode(wrongRequest))
                        .isInstanceOf(ReviewGroupUnauthorizedException.class)
        );
    }

    @Test
    void 리뷰_요청_코드로_리뷰_그룹을_반환한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, "groupAccessCode"));

        // when
        ReviewGroup actual = reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode);

        // then
        assertThat(actual).isEqualTo(savedReviewGroup);
    }

    @Test
    void 리뷰_요청_코드로_리뷰_그룹을_찾을_수_없는_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";

        // when, then
        assertThatThrownBy(() -> reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
