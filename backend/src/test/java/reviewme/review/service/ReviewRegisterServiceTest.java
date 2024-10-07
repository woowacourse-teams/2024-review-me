package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
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
import reviewme.review.repository.NewReviewRepository;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewRegisterServiceTest {

    @Autowired
    private ReviewRegisterService reviewRegisterService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private NewReviewRepository reviewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void 요청한_내용으로_리뷰를_등록한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question requiredCheckQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup requiredOptionGroup = optionGroupRepository.save(선택지_그룹(requiredCheckQuestion.getId()));
        OptionItem requiredOptionItem1 = optionItemRepository.save(선택지(requiredOptionGroup.getId()));
        OptionItem requiredOptionItem2 = optionItemRepository.save(선택지(requiredOptionGroup.getId()));
        Section visibleSection = sectionRepository.save(항상_보이는_섹션(List.of(requiredCheckQuestion.getId()), 1));

        Question requiredTextQuestion = questionRepository.save(서술형_필수_질문());
        Question conditionalCheckQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup conditionalOptionGroup = optionGroupRepository.save(선택지_그룹(conditionalCheckQuestion.getId()));
        OptionItem conditionalOptionItem1 = optionItemRepository.save(선택지(conditionalOptionGroup.getId()));
        OptionItem conditionalOptionItem2 = optionItemRepository.save(선택지(conditionalOptionGroup.getId()));
        Section conditionalSection = sectionRepository.save(조건부로_보이는_섹션(
                List.of(requiredCheckQuestion.getId(), requiredTextQuestion.getId(), conditionalCheckQuestion.getId()),
                requiredOptionItem1.getId(), 2)
        );

        Question optionalTextQuestion = questionRepository.save(서술형_옵션_질문());
        Section visibleOptionalSection = sectionRepository.save(항상_보이는_섹션(
                List.of(optionalTextQuestion.getId()), 3)
        );

        Template template = templateRepository.save(템플릿(
                List.of(visibleSection.getId(), conditionalSection.getId(), visibleOptionalSection.getId())));

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
        long registeredReviewId = reviewRegisterService.registerReview(reviewRegisterRequest);

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
