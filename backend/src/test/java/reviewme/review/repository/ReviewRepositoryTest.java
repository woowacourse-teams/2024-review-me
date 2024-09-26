package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 리뷰_그룹_아이디에_해당하는_모든_리뷰를_생성일_기준_내림차순으로_불러온다() {
        // given
        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Review review1 = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), null, null));
        Review review2 = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), null, null));

        // when
        List<Review> actual = reviewRepository.findAllByGroupId(reviewGroup.getId());

        // then
        assertThat(actual).containsExactly(review2, review1);
    }

    @Nested
    class 리뷰그룹_아이디에_해당하는_리뷰를_생성일_기준_내림차순으로_페이징하여_불러온다 {

        private final Question question = questionRepository.save(서술형_필수_질문());
        private final Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        private final Template template = templateRepository.save(템플릿(List.of(section.getId())));
        private final ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        private final Review review1 = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), null, null));
        private final Review review2 = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), null, null));
        private final Review review3 = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), null, null));


        @Test
        void 페이징_크기보다_적은_수의_리뷰가_등록되었으면_그_크기만큼의_리뷰만_반환한다() {
            // given
            int limit = 5;
            long lastReviewId = Long.MAX_VALUE;

            // when
            List<Review> actual = reviewRepository.findByReviewGroupIdWithLimit(
                    reviewGroup.getId(), lastReviewId, limit);

            // then
            assertThat(actual)
                    .hasSize(3)
                    .containsExactly(review3, review2, review1);
        }

        @Test
        void 페이징_크기보다_큰_수의_리뷰가_등록되었으면_페이징_크기만큼의_리뷰를_반환한다() {
            // given
            int limit = 2;
            long lastReviewId = Long.MAX_VALUE;

            // when
            List<Review> actual = reviewRepository.findByReviewGroupIdWithLimit(
                    reviewGroup.getId(), lastReviewId, limit);

            // then
            assertThat(actual)
                    .hasSize(2)
                    .containsExactly(review3, review2);
        }

        @Test
        void 마지막_리뷰_아이디를_기준으로_그보다_전에_적힌_리뷰를_반환한다() {
            // given
            int limit = 5;
            long lastReviewId = review3.getId();

            // when
            List<Review> actual = reviewRepository.findByReviewGroupIdWithLimit(
                    reviewGroup.getId(), lastReviewId, limit);

            // then
            assertThat(actual)
                    .hasSize(2)
                    .containsExactly(review2, review1);
        }

        @Test
        void 마지막으로_온_리뷰_전에_작성된_리뷰가_없으면_빈_리스트를_반환한다() {
            // given
            int limit = 5;
            long lastReviewId = review1.getId();

            // when
            List<Review> actual = reviewRepository.findByReviewGroupIdWithLimit(
                    reviewGroup.getId(), lastReviewId, limit);

            // then
            assertThat(actual).isEmpty();
        }
    }
}
