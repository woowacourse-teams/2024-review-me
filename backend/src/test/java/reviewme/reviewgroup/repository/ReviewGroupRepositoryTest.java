package reviewme.reviewgroup.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.회원_지정_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse;

@DataJpaTest
class ReviewGroupRepositoryTest {

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Nested
    @DisplayName("회원 아이디에 해당하는 리뷰 그룹을 생성일 기준 내림차순으로 페이징하여 불러온다")
    class FindByMemberIdWithLimit {

        @Test
        void lastReviewGroupId가_주어지지_않을_경우_가장_최근의_리뷰_그룹부터_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup1 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup2 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup3 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, null, 10);

            // then
            assertThat(response).extracting(ReviewGroupPageElementResponse::reviewGroupId)
                    .containsExactly(reviewGroup3.getId(), reviewGroup2.getId(), reviewGroup1.getId());
        }

        @Test
        void lastReviewGroupId가_주어질_경우_해당ID_이후의_가장_최근의_리뷰_그룹부터_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup1 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup2 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup3 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup4 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));

            long lastReviewGroupId = reviewGroup3.getId();

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, lastReviewGroupId, 10);

            // then
            assertThat(response).extracting(ReviewGroupPageElementResponse::reviewGroupId)
                    .containsExactly(reviewGroup2.getId(), reviewGroup1.getId());
        }

        @Test
        void lastReviewGroupId_보다_이후에_등록된_리뷰_그룹이_없으면_빈_리스트를_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup1 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup2 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            ReviewGroup reviewGroup3 = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));

            long lastReviewGroupId = reviewGroup1.getId();

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, lastReviewGroupId, 10);

            // then
            assertThat(response).isEmpty();
        }

        @Test
        void 페이징_크기보다_적은_수의_리뷰_그룹이_등록되었으면_등록된_크기만큼만_반환한다() {
            // given
            long memberId = 1L;
            int numberOfReviewGroup = 4;
            int limit = 6;

            for (int i = 0; i < numberOfReviewGroup; i++) {
                reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            }

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, null, limit);

            // then
            assertThat(response).hasSize(numberOfReviewGroup);
        }

        @Test
        void 페이징_크기보다_큰_수의_리뷰_그룹이_등록되었으면_페이징_크기만큼만_반환한다() {
            // given
            long memberId = 1L;
            int numberOfReviewGroup = 4;
            int limit = 3;

            for (int i = 0; i < numberOfReviewGroup; i++) {
                reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));
            }

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, null, limit);

            // then
            assertThat(response).hasSize(limit);
        }

        @Test
        void 리뷰_그룹에_등록된_리뷰_개수를_함께_응답한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_지정_리뷰_그룹(memberId));

            int reviewCount = 3;
            for (int i = 0; i < reviewCount; i++) {
                reviewRepository.save(비회원_작성_리뷰(reviewGroup.getTemplateId(), reviewGroup.getId(), List.of()));
            }

            // when
            List<ReviewGroupPageElementResponse> response = reviewGroupRepository.findAllByMemberIdWithLimit(
                    memberId, null, 10);

            // then
            assertThat(response).extracting(ReviewGroupPageElementResponse::reviewCount).containsOnly((long) reviewCount);
        }
    }
}
