package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.dto.request.create.CreateReviewRequest;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.Review2Repository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class CreateReviewServiceTest {

    @Autowired
    private CreateReviewService createReviewService;

    @Autowired
    private Question2Repository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private Review2Repository reviewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TextAnswerRepository textAnswerRepository;

    @Autowired
    private CheckboxAnswerRepository checkboxAnswerRepository;

    private ReviewGroup savedReviewGroup;

    private String reviewRequestCode = "리뷰요청코드";

    @BeforeEach
    void setUp() {
        savedReviewGroup = reviewGroupRepository.save(
                new ReviewGroup("리뷰어", "프로젝트", reviewRequestCode, "그룹접근코드")
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
        String expectedTextAnswer = "서술형답변";
        Question2 savedQuestion = questionRepository.save(
                new Question2(true, QuestionType.TEXT, "질문", "가이드라인", 1)
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
    void 체크박스가_포함된_리뷰를_저장한다() {
        // given
        Question2 savedQuestion = questionRepository.save(
                new Question2(true, QuestionType.CHECKBOX, "질문", "가이드라인", 1)
        );
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 2, 2)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(
                new OptionItem("선택지1", savedOptionGroup.getId(), 1)
        );
        OptionItem savedOptionItem2 = optionItemRepository.save(
                new OptionItem("선택지2", savedOptionGroup.getId(), 2)
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
//      아래 코드 검증하려면 fetch = FetchType.EAGER로 설정해야함,
//      그런데 테스트코드에서만 필요한 설정이라면?? 이 윗부분까지만의 검증으로 충분할수도 있음
//      그런데 다른 사람이 이미 EAGER 설정을 했다면 아래 주석 해제하고 검증 바로 하면 됨
//     assertThat(checkboxAnswerRepository.findAll().get(0).getSelectedOptionIds())
//                .hasSize(2);
    }
}
