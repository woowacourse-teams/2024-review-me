package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewSummaryServiceTest {

    @Autowired
    private ReviewSummaryService reviewSummaryService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 리뷰_그룹에_등록된_리뷰_요약_정보를_반환한다() {
        // given
        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        List<Review> reviews = List.of(
                new Review(template.getId(), reviewGroup.getId(), List.of()),
                new Review(template.getId(), reviewGroup.getId(), List.of()),
                new Review(template.getId(), reviewGroup.getId(), List.of())
        );
        reviewRepository.saveAll(reviews);
        // when
        ReceivedReviewsSummaryResponse actual = reviewSummaryService.getReviewSummary(
                reviewGroup.getReviewRequestCode());
        // then
        assertAll(
                () -> assertThat(actual.projectName()).isEqualTo(reviewGroup.getProjectName()),
                () -> assertThat(actual.revieweeName()).isEqualTo(reviewGroup.getReviewee()),
                () -> assertThat(actual.totalReviewCount()).isEqualTo(reviews.size())
        );
    }

    @Test
    void 리뷰_요약_정보_조회시_리뷰_요청_코드가_존재하지_않는_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when, then
        assertThatThrownBy(() -> reviewSummaryService.getReviewSummary(
                reviewGroup.getReviewRequestCode() + "wrong"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
