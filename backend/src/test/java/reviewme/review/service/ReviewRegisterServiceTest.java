package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
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
class ReviewRegisterServiceTest {

    @Autowired
    private ReviewRegisterService reviewRegisterService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 요청한_내용으로_리뷰를_등록한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        OptionItem requiredOptionItem1 = 선택지();
        OptionItem requiredOptionItem2 = 선택지();
        OptionGroup requiredOptionGroup = 선택지_그룹(List.of(requiredOptionItem1, requiredOptionItem2));
        Question requiredCheckQuestion = new Question(true, QuestionType.CHECKBOX, requiredOptionGroup, "질문", "설명", 1);
        Section visibleSection = 항상_보이는_섹션(List.of(requiredCheckQuestion), 1);

        OptionItem conditionalOptionItem1 = 선택지();
        OptionItem conditionalOptionItem2 = 선택지();
        OptionGroup conditionalOptionGroup = 선택지_그룹(List.of(conditionalOptionItem1, conditionalOptionItem2));
        Question requiredTextQuestion = 서술형_필수_질문();
        Question conditionalCheckQuestion = new Question(false, QuestionType.CHECKBOX, conditionalOptionGroup, "질문",
                "설명", 1);
        Section conditionalSection = 조건부로_보이는_섹션(
                List.of(requiredCheckQuestion, requiredTextQuestion, conditionalCheckQuestion), requiredOptionItem1, 2
        );

        Question optionalTextQuestion = 서술형_옵션_질문();
        Section visibleOptionalSection = 항상_보이는_섹션(List.of(optionalTextQuestion), 3);
        templateRepository.save(
                new Template(List.of(visibleSection, conditionalSection, visibleOptionalSection))
        );

        ReviewAnswerRequest requiredCheckQuestionAnswer = new ReviewAnswerRequest(
                requiredCheckQuestion.getId(), List.of(requiredOptionItem1.getId()), null);
        ReviewAnswerRequest requiredTextQuestionAnswer = new ReviewAnswerRequest(
                requiredTextQuestion.getId(), null, "답변".repeat(30));
        ReviewAnswerRequest conditionalCheckQuestionAnswer = new ReviewAnswerRequest(
                conditionalCheckQuestion.getId(), List.of(conditionalOptionItem1.getId()), null);
        ReviewAnswerRequest optionalTextQuestionAnswer = new ReviewAnswerRequest(
                optionalTextQuestion.getId(), null, "");
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(requiredCheckQuestionAnswer, requiredTextQuestionAnswer, conditionalCheckQuestionAnswer,
                        optionalTextQuestionAnswer));

        // when
        long registeredReviewId = reviewRegisterService.registerReview(reviewRegisterRequest, null);

        // when, then
        Review review = reviewRepository.findById(registeredReviewId).orElseThrow();
        assertAll(
                () -> assertThat(review.getAnswersByType(TextAnswer.class)).extracting(TextAnswer::getQuestionId)
                        .containsExactly(requiredTextQuestion.getId()),
                () -> assertThat(review.getAnswersByType(CheckboxAnswer.class)).extracting(
                                CheckboxAnswer::getQuestionId)
                        .containsAll(List.of(requiredCheckQuestion.getId(), conditionalCheckQuestion.getId()))
        );
    }
}
