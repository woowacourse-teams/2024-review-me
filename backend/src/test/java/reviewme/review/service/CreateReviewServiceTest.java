package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.UnnecessaryQuestionIncludedException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
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

    @Autowired
    private ReviewGroupFixture reviewGroupFixture;

    @Autowired
    private QuestionFixture questionFixture;

    @Autowired
    private OptionGroupFixture optionGroupFixture;

    @Autowired
    private OptionItemFixture optionItemFixture;

    @Autowired
    private SectionFixture sectionFixture;

    @Autowired
    private TemplateFixture templateFixture;

    @Test
    void 필수_질문에_모두_응답하는_경우_예외가_발생하지_않는다() {
        // 리뷰 그룹 저장
        String reviewRequestCode = "1234";
        reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹(reviewRequestCode, "12341234"));

        // 필수 선택형 질문, 섹션 저장
        Question alwaysRequiredQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup alwaysRequiredOptionGroup = optionGroupRepository.save(
                optionGroupFixture.선택지_그룹(alwaysRequiredQuestion.getId())
        );
        OptionItem alwaysRequiredOptionItem1 = optionItemRepository.save(
                optionItemFixture.선택지(alwaysRequiredOptionGroup.getId(), 1)
        );
        OptionItem alwaysRequiredOptionItem2 = optionItemRepository.save(
                optionItemFixture.선택지(alwaysRequiredOptionGroup.getId(), 2)
        );
        Section alwaysRequiredSection = sectionRepository.save(
                sectionFixture.항상_보이는_섹션(List.of(alwaysRequiredQuestion.getId()))
        );

        // 필수가 아닌 서술형 질문 저장
        Question notRequiredQuestion = questionRepository.save(questionFixture.서술형_옵션_질문());
        Section notRequiredSection = sectionRepository.save(
                sectionFixture.항상_보이는_섹션(List.of(notRequiredQuestion.getId()), 1)
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션1 저장
        Question conditionalTextQuestion1 = questionRepository.save(questionFixture.서술형_필수_질문(1));
        Question conditionalCheckQuestion = questionRepository.save(questionFixture.선택형_필수_질문(2));
        OptionGroup conditionalOptionGroup = optionGroupRepository.save(
                optionGroupFixture.선택지_그룹(conditionalCheckQuestion.getId())
        );
        OptionItem conditionalOptionItem = optionItemRepository.save(
                optionItemFixture.선택지(conditionalOptionGroup.getId())
        );
        Section conditionalSection1 = sectionRepository.save(
                sectionFixture.조건부로_보이는_섹션(List.of(conditionalTextQuestion1.getId(), conditionalCheckQuestion.getId()),
                        alwaysRequiredOptionItem1.getId(), 2));

        // optionItem 선택에 따라서 required 가 달라지는 섹션2 저장
        Question conditionalQuestion2 = questionRepository.save(questionFixture.서술형_필수_질문());
        Section conditionalSection2 = sectionRepository.save(
                sectionFixture.조건부로_보이는_섹션(List.of(conditionalQuestion2.getId()), alwaysRequiredOptionItem2.getId(), 3)
        );

        // 템플릿 저장
        templateRepository.save(
                templateFixture.템플릿(
                        List.of(alwaysRequiredSection.getId(), conditionalSection1.getId(),
                                conditionalSection2.getId(), notRequiredSection.getId()))
        );

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
        reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹(reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(1L)));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(20);
        Question savedQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
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
        reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹(reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(1L)));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        Question savedQuestion = questionRepository.save(questionFixture.서술형_옵션_질문());
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
        reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹(reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(1L)));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        Question savedQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup savedOptionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(savedQuestion.getId()));
        OptionItem savedOptionItem1 = optionItemRepository.save(optionItemFixture.선택지(savedOptionGroup.getId(), 1));
        OptionItem savedOptionItem2 = optionItemRepository.save(optionItemFixture.선택지(savedOptionGroup.getId(), 2));
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
        reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹(reviewRequestCode, "12341234"));
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(1L)));
        templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(1000);
        Question savedQuestion = questionRepository.save(questionFixture.서술형_필수_질문());

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
