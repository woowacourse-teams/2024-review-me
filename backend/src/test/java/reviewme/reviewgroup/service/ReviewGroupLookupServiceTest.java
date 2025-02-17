package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.회원_지정_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupSummaryResponse;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewGroupLookupServiceTest {

    @Autowired
    ReviewGroupLookupService reviewGroupLookupService;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void 리뷰_요약_조회_시_리뷰_요청_코드로_리뷰_그룹을_조회한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup(
                1L,
                "ted",
                "review-me",
                "reviewRequestCode",
                "groupAccessCode"
        ));

        // when
        ReviewGroupSummaryResponse response = reviewGroupLookupService.getReviewGroupSummary(
                reviewGroup.getReviewRequestCode()
        );

        // then
        assertAll(
                () -> assertThat(response.revieweeName()).isEqualTo(reviewGroup.getReviewee()),
                () -> assertThat(response.projectName()).isEqualTo(reviewGroup.getProjectName())
        );
    }

    @Test
    void 리뷰_요약_조회_시_리뷰_요청_코드에_대한_리뷰_그룹이_존재하지_않을_경우_예외가_발생한다() {
        // given, when, then
        assertThatThrownBy(() -> reviewGroupLookupService.getReviewGroupSummary("reviewRequestCode"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 회원에게_등록된_리뷰_그룹을_페이지네이션을_적용하여_반환한다() {
        // given - 회원 id 설정
        long memberId = 1L;

        // given - 리뷰 그룹 등록
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
        ReviewGroup reviewGroup3 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
        ReviewGroup reviewGroup4 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
        ReviewGroup reviewGroup5 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));

        // given - 리뷰 그룹에 리뷰 등록
        reviewRepository.save(비회원_작성_리뷰(reviewGroup2.getTemplateId(), reviewGroup2.getId(), List.of()));

        reviewRepository.save(비회원_작성_리뷰(reviewGroup3.getTemplateId(), reviewGroup3.getId(), List.of()));
        reviewRepository.save(비회원_작성_리뷰(reviewGroup3.getTemplateId(), reviewGroup3.getId(), List.of()));

        // given - 페이지네이션 설정
        long lastReviewGroupId = reviewGroup4.getId();
        int size = 2;

        // when
        ReviewGroupPageResponse response = reviewGroupLookupService.getReviewGroupsByMember(
                lastReviewGroupId, size, memberId);

        // then
        assertAll(
                () -> assertThat(response.lastReviewGroupId()).isEqualTo(reviewGroup2.getId()),
                () -> assertThat(response.isLastPage()).isFalse(),
                () -> assertThat(response.reviewGroups()).extracting(ReviewGroupPageElementResponse::reviewGroupId)
                        .containsExactly(reviewGroup3.getId(), reviewGroup2.getId()),
                () -> assertThat(response.reviewGroups()).extracting(ReviewGroupPageElementResponse::reviewCount)
                        .containsExactly(2L, 1L)
        );
    }
}
