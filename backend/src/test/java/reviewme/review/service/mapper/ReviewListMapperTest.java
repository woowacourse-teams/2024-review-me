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
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
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
    private ReviewRepository reviewRepository;

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
        TextAnswer textAnswer = new TextAnswer(question.getId(), "텍스트형 응답");
        Review review1 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review2 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review3 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review4 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review5 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review6 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review7 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review8 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review9 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        Review review10 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
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
