package reviewme.review.service.module;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.OptionGroupFixture;
import reviewme.fixture.OptionItemFixture;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.fixture.SectionFixture;
import reviewme.fixture.TemplateFixture;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.QuestionNotAnsweredException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private  OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    private ReviewGroupFixture reviewGroupFixture = new ReviewGroupFixture();
    private OptionItemFixture optionItemFixture = new OptionItemFixture();
    private OptionGroupFixture optionGroupFixture = new OptionGroupFixture();
    private QuestionFixture questionFixture = new QuestionFixture();
    private SectionFixture sectionFixture = new SectionFixture();
    private TemplateFixture templateFixture = new TemplateFixture();

    @Test
    void 텍스트가_포함된_리뷰를_생성한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question question = questionRepository.save(questionFixture.서술형_필수_질문());
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(20);
        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(), null, expectedTextAnswer);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(), List.of(reviewAnswerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getTextAnswers()).hasSize(1);
    }

    @Test
    void 체크박스가_포함된_리뷰를_저장한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question question = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(question.getId()));
        OptionItem optionItem1 = optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));

        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(), List.of(optionItem1.getId()), null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(), List.of(reviewAnswerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getCheckboxAnswers()).hasSize(1);
    }

    @Test
    void 응답하지_않은_서술형_질문이_존재하는_리뷰를_생성할_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question textQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
        Question checkQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(textQuestion.getId()));
        OptionItem optionItem1 = optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));

        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(textQuestion.getId(), checkQuestion.getId())));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        ReviewAnswerRequest textAnswerRequest = new ReviewAnswerRequest(textQuestion.getId(), null, null);
        ReviewAnswerRequest checkAnswerRequest = new ReviewAnswerRequest(checkQuestion.getId(), List.of(optionItem1.getId()), null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewGroup.getReviewRequestCode(), List.of(textAnswerRequest, checkAnswerRequest));

        // when, then
        assertThatCode(() -> reviewMapper.mapToReview(reviewRegisterRequest))
                .isInstanceOf(QuestionNotAnsweredException.class);
    }

    @Test
    void 응답하지_않은_선택형_질문이_존재하는_리뷰를_생성할_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question textQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
        Question checkQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(textQuestion.getId()));
        optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));
        optionItemRepository.save(optionItemFixture.선택지(optionGroup.getId()));

        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(textQuestion.getId(), checkQuestion.getId())));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        ReviewAnswerRequest textAnswerRequest = new ReviewAnswerRequest(textQuestion.getId(), null, "답변".repeat(20));
        ReviewAnswerRequest checkAnswerRequest = new ReviewAnswerRequest(checkQuestion.getId(), null, null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewGroup.getReviewRequestCode(), List.of(textAnswerRequest, checkAnswerRequest));

        // when, then
        assertThatCode(() -> reviewMapper.mapToReview(reviewRegisterRequest))
                .isInstanceOf(QuestionNotAnsweredException.class);
    }

    @Test
    void 잘못된_리뷰_요청_코드로_리뷰를_생성할_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "notExistCode";
        Question savedQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
        ReviewAnswerRequest emptyTextReviewRequest = new ReviewAnswerRequest(
                savedQuestion.getId(), null, "");
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewRequestCode, List.of(emptyTextReviewRequest));

        // when, then
        assertThatThrownBy(() -> reviewMapper.mapToReview(
                reviewRegisterRequest))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 답변에_해당하는_질문이_없는_리뷰를_생성할_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question question = questionRepository.save(questionFixture.서술형_필수_질문());
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        long notSavedQuestionId = 100L;
        ReviewAnswerRequest notQuestionAnswerRequest = new ReviewAnswerRequest(
                notSavedQuestionId, null, "");
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewGroup.getReviewRequestCode(), List.of(notQuestionAnswerRequest));

        // when, then
        assertThatThrownBy(() -> reviewMapper.mapToReview(
                reviewRegisterRequest))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }
}
