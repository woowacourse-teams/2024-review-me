package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.exception.ReviewNotFoundByIdAndGroupException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
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
    private TemplateRepository templateRepository;

    @Test
    void 리뷰_그룹에_해당하지_않는_리뷰를_조회할_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(리뷰_그룹());
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(리뷰_그룹());

        Review review1 = reviewRepository.save(new Review(0, reviewGroup1.getId(), List.of()));
        Review review2 = reviewRepository.save(new Review(0, reviewGroup2.getId(), List.of()));

        // when, then
        assertAll(
                () -> assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(review2.getId(), reviewGroup1))
                        .isInstanceOf(ReviewNotFoundByIdAndGroupException.class),
                () -> assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(review1.getId(), reviewGroup2))
                        .isInstanceOf(ReviewNotFoundByIdAndGroupException.class)
        );
    }

    @Test
    void 사용자가_작성한_리뷰를_확인한다() {
        // given - 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 질문 저장
        OptionItem optionItem1 = 선택지();
        OptionItem optionItem2 = 선택지();
        OptionGroup optionGroup = 선택지_그룹(List.of(optionItem1, optionItem2));
        Question question1 = new Question(true, QuestionType.CHECKBOX, optionGroup, "질문1", "설명1", 1);
        Question question2 = 서술형_필수_질문();

        // given - 섹션, 템플릿 저장
        Section section1 = 항상_보이는_섹션(List.of(question1));
        Section section2 = 항상_보이는_섹션(List.of(question2));
        Template template = templateRepository.save(new Template(List.of(section1, section2)));

        // given - 리뷰 답변 저장
        List<Answer> answers = List.of(
                new TextAnswer(question2.getId(), "답변".repeat(20)),
                new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId(), optionItem2.getId()))
        );
        Review review = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), answers)
        );

        // when
        ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(review.getId(), reviewGroup);

        // then
        assertThat(reviewDetail.sections()).hasSize(2);
    }

    @Nested
    @DisplayName("필수가 아닌 답변에 응답하지 않았을 때")
    class NotAnsweredOptionalQuestion {

        @Test
        void 섹션에_필수가_아닌_질문만_있다면_섹션_자체를_반환하지_않는다() {
            // given - 리뷰 그룹 저장
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

            // given - 질문, 세션, 템플릿 저장
            Question question = 서술형_옵션_질문(1);
            Section section = 항상_보이는_섹션(List.of(question));
            Template template = templateRepository.save(new Template(List.of(section)));

            // given - 아무것도 응답하지 않은 리뷰 답변 저장
            Review review = reviewRepository.save(
                    new Review(template.getId(), reviewGroup.getId(), null)
            );

            // when
            ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(review.getId(), reviewGroup);

            // then
            assertThat(reviewDetail.sections())
                    .extracting(SectionAnswerResponse::sectionId)
                    .isEmpty();
        }

        @Test
        void 섹션의_다른_질문에_응답했다면_답하지_않은_질문만_반환하지_않는다() {
            // given - 리뷰 그룹 저장
            ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

            // given - 질문, 세션, 템플릿 저장
            Question question1 = 서술형_옵션_질문(1);
            Question question2 = 서술형_옵션_질문(2);
            Section section = 항상_보이는_섹션(List.of(question1, question2));
            Template template = templateRepository.save(new Template(List.of(section)));

            // given - 질문 하나에만 응답한 리뷰 답변 저장
            TextAnswer textAnswer = new TextAnswer(question1.getId(), "답변".repeat(20));
            Review review = reviewRepository.save(
                    new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer))
            );

            // when
            ReviewDetailResponse reviewDetail = reviewDetailLookupService.getReviewDetail(review.getId(), reviewGroup);

            // then
            assertAll(
                    () -> assertThat(reviewDetail.sections())
                            .extracting(SectionAnswerResponse::sectionId)
                            .containsExactly(section.getId()),
                    () -> assertThat(reviewDetail.sections())
                            .flatExtracting(SectionAnswerResponse::questions)
                            .extracting(QuestionAnswerResponse::questionId)
                            .containsExactly(question1.getId())
            );
        }
    }
}
