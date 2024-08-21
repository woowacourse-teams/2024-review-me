package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.UnnecessaryQuestionIncludedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class CreateReviewServiceTest {

    @Autowired
    private CreateReviewService createReviewService;

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
    private ReviewRepository reviewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TextAnswerRepository textAnswerRepository;

    @Autowired
    private CheckboxAnswerRepository checkboxAnswerRepository;

    @Test
    void 필수_질문에_모두_응답하는_경우_예외가_발생하지_않는다() {
        // 리뷰 그룹 저장
        String reviewRequestCode = "1234";
        reviewGroupRepository.save(new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234"));

        // 필수 선택형 질문, 섹션 저장
        Question alwaysRequiredQuestion = questionRepository.save(
                new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1)
        );
        OptionGroup alwaysRequiredOptionGroup = optionGroupRepository.save(
                new OptionGroup(alwaysRequiredQuestion.getId(), 1, 2)
        );
        OptionItem alwaysRequiredOptionItem1 = optionItemRepository.save(
                new OptionItem("선택지", alwaysRequiredOptionGroup.getId(), 1, OptionType.KEYWORD)
        );
        OptionItem alwaysRequiredOptionItem2 = optionItemRepository.save(
                new OptionItem("선택지", alwaysRequiredOptionGroup.getId(), 2, OptionType.KEYWORD)
        );
        Section alwaysRequiredSection = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(alwaysRequiredQuestion.getId()), null, "섹션명", "말머리", 1)
        );

        // 필수가 아닌 서술형 질문 저장
        Question notRequiredQuestion = questionRepository.save(
                new Question(false, QuestionType.TEXT, "질문", "가이드라인", 1)
        );
        Section notRequiredSection = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(notRequiredQuestion.getId()), null, "섹션명", "말머리", 1)
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션1 저장
        Question conditionalTextQuestion1 = questionRepository.save(
                new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1)
        );
        Question conditionalCheckQuestion = questionRepository.save(
                new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1)
        );
        OptionGroup conditionalOptionGroup = optionGroupRepository.save(
                new OptionGroup(conditionalCheckQuestion.getId(), 1, 2)
        );
        OptionItem conditionalOptionItem = optionItemRepository.save(
                new OptionItem("선택지", conditionalOptionGroup.getId(), 1, OptionType.KEYWORD)
        );
        Section conditionalSection1 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL,
                        List.of(conditionalTextQuestion1.getId(), conditionalCheckQuestion.getId()),
                        alwaysRequiredOptionItem1.getId(), "섹션명", "말머리", 1)
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션2 저장
        Question conditionalQuestion2 = questionRepository.save(
                new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1)
        );
        Section conditionalSection2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(conditionalQuestion2.getId()),
                        alwaysRequiredOptionItem2.getId(), "섹션명", "말머리", 1)
        );

        // 템플릿 저장
        templateRepository.save(new Template(
                List.of(alwaysRequiredSection.getId(), conditionalSection1.getId(),
                        conditionalSection2.getId(), notRequiredSection.getId())
        ));

        // 각 질문에 대한 답변 생성
        CreateReviewAnswerRequest alwaysRequiredAnswer = new CreateReviewAnswerRequest(
                alwaysRequiredQuestion.getId(), List.of(alwaysRequiredOptionItem1.getId()), null);
        CreateReviewAnswerRequest conditionalTextAnswer1 = new CreateReviewAnswerRequest(
                conditionalTextQuestion1.getId(), null, "답변".repeat(30));
        CreateReviewAnswerRequest conditionalCheckAnswer1 = new CreateReviewAnswerRequest(
                conditionalCheckQuestion.getId(), List.of(conditionalOptionItem.getId()), null);
        CreateReviewAnswerRequest conditionalTextAnswer2 = new CreateReviewAnswerRequest(
                conditionalQuestion2.getId(), null, "답변".repeat(30));

        // 상황별로 다르게 구성한 리뷰 생성 dto
        CreateReviewRequest properRequest = new CreateReviewRequest(
                reviewRequestCode, List.of(alwaysRequiredAnswer, conditionalTextAnswer1, conditionalCheckAnswer1));
        CreateReviewRequest selectedOptionIdQuestionMissingRequest1 = new CreateReviewRequest(
                reviewRequestCode, List.of(alwaysRequiredAnswer));
        CreateReviewRequest selectedOptionIdQuestionMissingRequest2 = new CreateReviewRequest(
                reviewRequestCode, List.of(alwaysRequiredAnswer, conditionalTextAnswer1));
        CreateReviewRequest selectedOptionIdQuestionMissingRequest3 = new CreateReviewRequest(
                reviewRequestCode, List.of(alwaysRequiredAnswer, conditionalCheckAnswer1));
        CreateReviewRequest unnecessaryQuestionIncludedRequest = new CreateReviewRequest(
                reviewRequestCode, List.of(alwaysRequiredAnswer, conditionalTextAnswer1,
                conditionalCheckAnswer1, conditionalTextAnswer2));

        // when, then
        assertThatCode(() -> createReviewService.createReview(properRequest))
                .doesNotThrowAnyException();
        assertThatCode(() -> createReviewService.createReview(selectedOptionIdQuestionMissingRequest1))
                .isInstanceOf(MissingRequiredQuestionException.class);
        assertThatCode(() -> createReviewService.createReview(selectedOptionIdQuestionMissingRequest2))
                .isInstanceOf(MissingRequiredQuestionException.class);
        assertThatCode(() -> createReviewService.createReview(selectedOptionIdQuestionMissingRequest3))
                .isInstanceOf(MissingRequiredQuestionException.class);
        assertThatCode(() -> createReviewService.createReview(unnecessaryQuestionIncludedRequest))
                .isInstanceOf(UnnecessaryQuestionIncludedException.class);
    }

    @Test
    void 텍스트가_포함된_리뷰를_저장한다() {
        // given
        String reviewRequestCode = "0000";
        reviewGroupRepository.save(new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(1L), 1L, "섹션명", "말머리", 1));
        templateRepository.save(new Template(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(20);
        Question savedQuestion = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1));
        CreateReviewAnswerRequest createReviewAnswerRequest = new CreateReviewAnswerRequest(savedQuestion.getId(), null,
                expectedTextAnswer);
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(reviewRequestCode,
                List.of(createReviewAnswerRequest));

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(textAnswerRepository.findAll()).hasSize(1);
    }

    @Test
    void 필수가_아닌_텍스트형_응답에_빈문자열이_들어오면_저장하지_않는다() {
        // given
        String reviewRequestCode = "0000";
        reviewGroupRepository.save(new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(1L), 1L, "섹션명", "말머리", 1));
        templateRepository.save(new Template(List.of(section.getId())));

        Question savedQuestion = questionRepository.save(
                new Question(false, QuestionType.TEXT, "질문", "가이드라인", 1));
        CreateReviewAnswerRequest emptyTextReviewRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), null, "");
        CreateReviewAnswerRequest validTextReviewRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), null, "질문 1 답변 (20자 이상 입력 적용)");
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(reviewRequestCode,
                List.of(emptyTextReviewRequest, validTextReviewRequest));

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(textAnswerRepository.findAll()).hasSize(1);
    }

    @Test
    void 체크박스가_포함된_리뷰를_저장한다() {
        // given
        String reviewRequestCode = "0000";
        reviewGroupRepository.save(new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(1L), 1L, "섹션명", "말머리", 1));
        templateRepository.save(new Template(List.of(section.getId())));

        Question savedQuestion = questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1));
        OptionGroup savedOptionGroup = optionGroupRepository.save(new OptionGroup(savedQuestion.getId(), 2, 2));
        OptionItem savedOptionItem1 = optionItemRepository.save(
                new OptionItem("선택지1", savedOptionGroup.getId(), 1, OptionType.KEYWORD));
        OptionItem savedOptionItem2 = optionItemRepository.save(
                new OptionItem("선택지2", savedOptionGroup.getId(), 2, OptionType.KEYWORD));
        CreateReviewAnswerRequest createReviewAnswerRequest = new CreateReviewAnswerRequest(savedQuestion.getId(),
                List.of(savedOptionItem1.getId(), savedOptionItem2.getId()), null);
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(reviewRequestCode,
                List.of(createReviewAnswerRequest));

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(checkboxAnswerRepository.findAll()).hasSize(1);
    }

    @Test
    void 적정_글자수인_텍스트_응답인_경우_정상_저장된다() {
        // given
        String reviewRequestCode = "0000";
        reviewGroupRepository.save(new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(1L), 1L, "섹션명", "말머리", 1));
        templateRepository.save(new Template(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(1000);
        Question savedQuestion = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1));
        CreateReviewAnswerRequest createReviewAnswerRequest = new CreateReviewAnswerRequest(savedQuestion.getId(), null,
                expectedTextAnswer);
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(reviewRequestCode,
                List.of(createReviewAnswerRequest));

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(textAnswerRepository.findAll()).hasSize(1);
    }
}
