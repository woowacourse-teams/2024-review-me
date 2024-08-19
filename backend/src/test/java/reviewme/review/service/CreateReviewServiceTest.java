package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    private final String reviewRequestCode = "리뷰요청코드";

    @BeforeEach
    void setUp() {
        reviewGroupRepository.save(
                new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "12341234")
        );
        templateRepository.save(
                new Template(List.of(1L))
        );
        sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(1L), 1L, "섹션", 1)
        );
    }

    @Test
    void 텍스트가_포함된_리뷰를_저장한다() {
        // given
        String expectedTextAnswer = "답".repeat(20);
        Question savedQuestion = questionRepository.save(
                new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1)
        );
        CreateReviewAnswerRequest createReviewAnswerRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), null, expectedTextAnswer
        );
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewRequestCode, List.of(createReviewAnswerRequest)
        );

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(textAnswerRepository.findAll()).hasSize(1);
    }

    @Test
    void 필수가_아닌_텍스트형_응답에_빈문자열이_들어오면_저장하지_않는다() {
        // given
        Question savedQuestion = questionRepository.save(
                new Question(false, QuestionType.TEXT, "질문", "가이드라인", 1)
        );
        CreateReviewAnswerRequest emptyTextReviewRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), null, ""
        );
        CreateReviewAnswerRequest validTextReviewRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), null, "질문 1 답변 (20자 이상 입력 적용)"
        );
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewRequestCode, List.of(emptyTextReviewRequest, validTextReviewRequest)
        );

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(textAnswerRepository.findAll()).hasSize(1);
    }

    @Test
    void 체크박스가_포함된_리뷰를_저장한다() {
        // given
        Question savedQuestion = questionRepository.save(
                new Question(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1)
        );
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 2, 2)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(
                new OptionItem("선택지1", savedOptionGroup.getId(), 1, OptionType.KEYWORD)
        );
        OptionItem savedOptionItem2 = optionItemRepository.save(
                new OptionItem("선택지2", savedOptionGroup.getId(), 2, OptionType.KEYWORD)
        );
        CreateReviewAnswerRequest createReviewAnswerRequest = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(savedOptionItem1.getId(), savedOptionItem2.getId()), null
        );
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewRequestCode, List.of(createReviewAnswerRequest)
        );

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertThat(reviewRepository.findAll()).hasSize(1);
        assertThat(checkboxAnswerRepository.findAll()).hasSize(1);
    }
}
