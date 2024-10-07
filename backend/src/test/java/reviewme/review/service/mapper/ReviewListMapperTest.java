package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.NewReview;
import reviewme.review.domain.NewTextAnswer;
import reviewme.review.repository.NewReviewRepository;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewListMapperTest {

    @Autowired
    private ReviewListMapper reviewListMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private NewReviewRepository reviewRepository;

    @Test
    void 리뷰_그룹에_있는_리뷰를_반환한다() {
        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 질문 저장
        Question question = questionRepository.save(선택형_필수_질문());

        // given - 섹션, 템플릿 저장
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        // given - 리뷰 답변 저장
        NewTextAnswer textAnswer = new NewTextAnswer(question.getId(), "텍스트형 응답");
        NewReview review1 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review2 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review3 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review4 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review5 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review6 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review7 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review8 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review9 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        NewReview review10 = new NewReview(template.getId(), reviewGroup.getId(), List.of(textAnswer));
        reviewRepository.saveAll(
                List.of(review1, review2, review3, review4, review5, review6, review7, review8, review9, review10));

        long lastReviewId = 8L;
        int size = 5;

        // when
        List<ReviewListElementResponse> responses = reviewListMapper.mapToReviewList(
                reviewGroup, lastReviewId, size);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(size),
                () -> assertThat(responses).extracting(ReviewListElementResponse::reviewId)
                        .containsExactly(
                                review7.getId(), review6.getId(), review5.getId(), review4.getId(), review3.getId())
        );
    }
}
