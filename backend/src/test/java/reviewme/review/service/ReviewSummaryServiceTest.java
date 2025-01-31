package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
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

    @Test
    void 리뷰_그룹에_등록된_리뷰_요약_정보를_반환한다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));

        ReviewGroup reviewGroup1 = reviewGroupRepository.save(리뷰_그룹());
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(리뷰_그룹());

        List<Review> reviews = List.of(
                비회원_작성_리뷰(template.getId(), reviewGroup1.getId(), List.of()),
                비회원_작성_리뷰(template.getId(), reviewGroup1.getId(), List.of()),
                비회원_작성_리뷰(template.getId(), reviewGroup1.getId(), List.of())
        );
        reviewRepository.saveAll(reviews);
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup2.getId(), List.of()));

        // when
        ReceivedReviewsSummaryResponse actual = reviewSummaryService.getReviewSummary(reviewGroup1.getId());

        // then
        assertAll(
                () -> assertThat(actual.projectName()).isEqualTo(reviewGroup1.getProjectName()),
                () -> assertThat(actual.revieweeName()).isEqualTo(reviewGroup1.getReviewee()),
                () -> assertThat(actual.totalReviewCount()).isEqualTo(reviews.size())
        );
    }
}
