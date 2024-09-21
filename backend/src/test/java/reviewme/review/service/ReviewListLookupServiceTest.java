package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지_카테고리;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewListLookupServiceTest {

    @Autowired
    private ReviewListLookupService reviewListLookupService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_요청_코드가_존재하지_않는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewListLookupService.getReceivedReviews("abc", "groupAccessCode"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 그룹_액세스_코드가_일치하지_않는_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "Jamsil";
        String groupAccessCode = "Seolleung";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

        // when, then
        assertThatThrownBy(() -> reviewListLookupService.getReceivedReviews(
                reviewRequestCode, "wrong" + groupAccessCode))
                .isInstanceOf(ReviewGroupUnauthorizedException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_내가_받은_리뷰_목록을_반환한다() {
        // given - 리뷰 그룹 저장
        String reviewRequestCode = "reviewRequestCode";
        String groupAccessCode = "groupAccessCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

        // given - 질문 저장
        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));
        OptionItem categoryOption = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 1));

        // given - 섹션, 템플릿 저장
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        // given - 리뷰 답변 저장
        CheckboxAnswer categoryAnswer = new CheckboxAnswer(question.getId(), List.of(categoryOption.getId()));
        Review review1 = new Review(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer));
        TextAnswer textAnswer = new TextAnswer(question.getId(), "텍스트형 응답");
        Review review2 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), List.of());
        reviewRepository.saveAll(List.of(review1, review2));

        // when
        ReceivedReviewsResponse response = reviewListLookupService.getReceivedReviews(reviewRequestCode,
                groupAccessCode);

        // then
        assertThat(response.reviews()).hasSize(2);
    }
}
