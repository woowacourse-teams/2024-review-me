package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.ReviewNotFoundByIdAndGroupException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewDetailLookupServiceTest {

    @Autowired
    private ReviewDetailLookupService reviewDetailLookupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 잘못된_리뷰_요청_코드로_리뷰를_조회할_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "hello";
        String groupAccessCode = "goodBye";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));
        Review review = reviewRepository.save(new Review(0, reviewGroup.getId(), List.of()));

        // when, then
        assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                review.getId(), "wrong" + reviewRequestCode
        )).isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 리뷰_그룹에_해당하지_않는_리뷰를_조회할_경우_예외가_발생한다() {
        // given
        String reviewRequestCode1 = "sancho";
        String groupAccessCode1 = "kirby";
        String reviewRequestCode2 = "aruru";
        String groupAccessCode2 = "tedChang";
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode1, groupAccessCode1));
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode2, groupAccessCode2));

        Review review1 = reviewRepository.save(new Review(0, reviewGroup1.getId(), List.of()));
        Review review2 = reviewRepository.save(new Review(0, reviewGroup2.getId(), List.of()));

        // when, then
        assertAll(
                () -> assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                        review2.getId(), reviewRequestCode1
                )).isInstanceOf(ReviewNotFoundByIdAndGroupException.class),
                () -> assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                        review1.getId(), reviewRequestCode2
                )).isInstanceOf(ReviewNotFoundByIdAndGroupException.class)
        );
    }

    @Test
    void 사용자가_작성한_리뷰를_확인한다() {
        // given - 리뷰 그룹 저장
        String reviewRequestCode = "1111";
        String groupAccessCode = "2222";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

        // given - 질문 저장
        Question question1 = questionRepository.save(선택형_필수_질문());
        Question question2 = questionRepository.save(서술형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId(), 2));

        // given - 섹션, 템플릿 저장
        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(question1.getId())));
        Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(question2.getId())));
        Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

        // given - 리뷰 답변 저장
        List<Answer> answers = List.of(
                new TextAnswer(question2.getId(), "답변".repeat(20)),
                new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId(), optionItem2.getId()))
        );
        Review review = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), answers)
        );

        // when
        ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(
                review.getId(), reviewRequestCode
        );

        // then
        assertThat(reviewDetail.sections()).hasSize(2);
    }

    @Nested
    @DisplayName("필수가 아닌 답변에 응답하지 않았을 때")
    class NotAnsweredOptionalQuestion {

        @Test
        void 섹션에_필수가_아닌_질문만_있다면_섹션_자체를_반환하지_않는다() {
            // given - 리뷰 그룹 저장
            String reviewRequestCode = "sancho";
            String groupAccessCode = "kirby";
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

            // given - 질문, 세션, 템플릿 저장
            Question question = questionRepository.save(서술형_옵션_질문(1));
            Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
            Template template = templateRepository.save(템플릿(List.of(section.getId())));

            // given - 아무것도 응답하지 않은 리뷰 답변 저장
            Review review = reviewRepository.save(
                    new Review(template.getId(), reviewGroup.getId(), null)
            );

            // when
            ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(
                    review.getId(), reviewRequestCode
            );

            // then
            assertThat(reviewDetail.sections())
                    .extracting(SectionAnswerResponse::sectionId)
                    .isEmpty();
        }

        @Test
        void 섹션의_다른_질문에_응답했다면_답하지_않은_질문만_반환하지_않는다() {
            // given - 리뷰 그룹 저장
            String reviewRequestCode = "aruru";
            String groupAccessCode = "tedChang";
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, groupAccessCode));

            // given - 질문, 세션, 템플릿 저장
            Question question1 = questionRepository.save(서술형_옵션_질문(1));
            Question question2 = questionRepository.save(서술형_옵션_질문(2));
            Section section = sectionRepository.save(항상_보이는_섹션(List.of(question1.getId(), question2.getId())));
            Template template = templateRepository.save(템플릿(List.of(section.getId())));

            // given - 질문 하나에만 응답한 리뷰 답변 저장
            TextAnswer textAnswer = new TextAnswer(question1.getId(), "답변".repeat(20));
            Review review = reviewRepository.save(
                    new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer))
            );

            // when
            ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(
                    review.getId(), reviewRequestCode
            );

            // then
            assertAll(
                    () -> assertThat(reviewDetail.sections())
                            .extracting(SectionAnswerResponse::sectionId)
                            .containsExactly(section.getId()),
                    () -> assertThat(reviewDetail.sections())
                            .flatExtracting(SectionAnswerResponse::reviews)
                            .extracting(QuestionAnswerResponse::questionId)
                            .containsExactly(question1.getId())
            );
        }
    }
}
