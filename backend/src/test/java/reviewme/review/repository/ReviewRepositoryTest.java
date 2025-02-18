package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewFixture.회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.회원_리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.review.domain.Review;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 리뷰_그룹_아이디에_해당하는_모든_리뷰를_생성일_기준_내림차순으로_불러온다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        Review review1 = reviewRepository.save(
                비회원_작성_리뷰(template.getId(), reviewGroup.getId(), null));
        Review review2 = reviewRepository.save(
                비회원_작성_리뷰(template.getId(), reviewGroup.getId(), null));

        // when
        List<Review> actual = reviewRepository.findAllByGroupId(reviewGroup.getId());

        // then
        assertThat(actual).containsExactly(review2, review1);
    }

    @Nested
    @DisplayName("리뷰 그룹 아이디에 해당하는 리뷰를 생성일 기준 내림차순으로 페이징하여 불러온다")
    class FindByReviewGroupIdWithLimit {
        @Test
        void lastReviewId가_주어지지_않을_경우_가장_최근의_리뷰부터_반환한다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));

            Long lastReviewId = null;

            // when
            List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, 10);

            // then
            assertThat(reviews).extracting(Review::getId)
                    .containsExactly(review3.getId(), review2.getId(), review1.getId());
        }

        @Test
        void lastReviewId가_주어질_경우_해당ID_이전의_가장_최근의_리뷰부터_반환한다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review4 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));

            long lastReviewId = review3.getId();

            // when
            List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, 10);

            // then
            assertThat(reviews).extracting(Review::getId)
                    .containsExactly(review2.getId(), review1.getId());
        }

        @Test
        void lastReviewId_보다_이전에_등록된_리뷰가_없으면_빈_리스트를_반환한다() {
            // given
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(비회원_작성_리뷰( 1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(비회원_작성_리뷰( 1L, reviewGroup.getId(), List.of()));

            long lastReviewId = review1.getId();

            // when
            List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, 10);

            // then
            assertThat(reviews).isEmpty();
        }

        @Test
        void 페이징_크기보다_적은_수의_리뷰가_등록되었으면_등록된_크기만큼만_반환한다() {
            // given
            int numberOfReview = 4;
            int limit = Integer.MAX_VALUE;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());

            for (int i = 0; i < numberOfReview; i++) {
                reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            }

            // when
            List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(reviewGroup.getId(), null, limit);

            // then
            assertThat(reviews).hasSize(numberOfReview);
        }

        @Test
        void 페이징_크기보다_큰_수의_리뷰가_등록되었으면_페이징_크기만큼만_반환한다() {
            // given
            int numberOfReviewGroup = 4;
            int limit = 3;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());

            for (int i = 0; i < numberOfReviewGroup; i++) {
                reviewRepository.save(비회원_작성_리뷰(1L, reviewGroup.getId(), List.of()));
            }

            // when
            List<Review> reviews = reviewRepository.findAllByReviewGroupIdWithLimit(reviewGroup.getId(), null, limit);

            // then
            assertThat(reviews).hasSize(limit);
        }
    }

    @Nested
    @DisplayName("회원 아이디에 해당하는 리뷰를 생성일 기준 내림차순으로 페이징하여 불러온다")
    class FindByMemberIdWithLimit {

        @Test
        void lastReviewId가_주어지지_않을_경우_가장_최근의_리뷰부터_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));

            Long lastReviewId = null;

            // when
            List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(memberId, lastReviewId, 10);

            // then
            assertThat(reviews).extracting(Review::getId)
                    .containsExactly(review3.getId(), review2.getId(), review1.getId());
        }

        @Test
        void lastReviewId가_주어질_경우_해당ID_이전의_가장_최근의_리뷰부터_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review4 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));

            long lastReviewId = review3.getId();

            // when
            List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(memberId, lastReviewId, 10);

            // then
            assertThat(reviews).extracting(Review::getId)
                    .containsExactly(review2.getId(), review1.getId());
        }

        @Test
        void lastReviewId_보다_이전에_등록된_리뷰가_없으면_빈_리스트를_반환한다() {
            // given
            long memberId = 1L;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
            Review review1 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review2 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            Review review3 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));

            long lastReviewId = review1.getId();

            // when
            List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(memberId, lastReviewId, 10);

            // then
            assertThat(reviews).isEmpty();
        }

        @Test
        void 페이징_크기보다_적은_수의_리뷰가_등록되었으면_등록된_크기만큼만_반환한다() {
            // given
            long memberId = 1L;
            int numberOfReview = 4;
            int limit = Integer.MAX_VALUE;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());

            for (int i = 0; i < numberOfReview; i++) {
                reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            }

            // when
            List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(memberId, null, limit);

            // then
            assertThat(reviews).hasSize(numberOfReview);
        }

        @Test
        void 페이징_크기보다_큰_수의_리뷰가_등록되었으면_페이징_크기만큼만_반환한다() {
            // given
            long memberId = 1L;
            int numberOfReviewGroup = 4;
            int limit = 3;
            ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());

            for (int i = 0; i < numberOfReviewGroup; i++) {
                reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
            }

            // when
            List<Review> reviews = reviewRepository.findAllByMemberIdWithLimit(memberId, null, limit);

            // then
            assertThat(reviews).hasSize(limit);
        }
    }
}
