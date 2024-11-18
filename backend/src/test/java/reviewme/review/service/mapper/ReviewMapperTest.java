package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.facade.mapper.ReviewMapper;
import reviewme.review.facade.request.ReviewAnswerRequest;
import reviewme.review.facade.request.ReviewRegisterRequest;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.domain.Template;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 텍스트가_포함된_리뷰를_생성한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(20);
        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(), null, expectedTextAnswer);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(reviewAnswerRequest));

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest.answers(), structuredTemplate, reviewGroup);

        // then
        assertThat(review.getAnswersByType(TextAnswer.class)).hasSize(1);
    }

    @Test
    void 체크박스가_포함된_리뷰를_생성한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(),
                List.of(optionItem1.getId()), null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(reviewAnswerRequest));

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question),
                List.of(optionGroup), List.of(optionItem1, optionItem2));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest.answers(), structuredTemplate, reviewGroup);

        // then
        assertThat(review.getAnswersByType(CheckboxAnswer.class)).hasSize(1);
    }

    @Test
    void 필수가_아닌_질문에_답변이_없을_경우_답변을_생성하지_않는다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question requiredTextQuestion = questionRepository.save(서술형_필수_질문());
        Question optionalTextQuestion = questionRepository.save(서술형_옵션_질문());

        Question requeiredCheckBoxQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(requeiredCheckBoxQuestion.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));

        Question optionalCheckBoxQuestion = questionRepository.save(선택형_옵션_질문());
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(optionalCheckBoxQuestion.getId()));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
        OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup2.getId()));

        Section section = sectionRepository.save(항상_보이는_섹션(
                List.of(requiredTextQuestion.getId(), optionalTextQuestion.getId(),
                        requeiredCheckBoxQuestion.getId(), optionalCheckBoxQuestion.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String textAnswer = "답".repeat(20);
        ReviewAnswerRequest requiredTextAnswerRequest = new ReviewAnswerRequest(
                requiredTextQuestion.getId(), null, textAnswer
        );
        ReviewAnswerRequest optionalTextAnswerRequest = new ReviewAnswerRequest(
                optionalTextQuestion.getId(), null, ""
        );
        ReviewAnswerRequest requiredCheckBoxAnswerRequest = new ReviewAnswerRequest(
                requeiredCheckBoxQuestion.getId(), List.of(optionItem1.getId()), null
        );
        ReviewAnswerRequest optionalCheckBoxAnswerRequest = new ReviewAnswerRequest(
                optionalCheckBoxQuestion.getId(), List.of(), null
        );
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(requiredTextAnswerRequest, optionalTextAnswerRequest,
                        requiredCheckBoxAnswerRequest, optionalCheckBoxAnswerRequest));

        List<Question> questions = questionRepository.findAll();
        List<OptionGroup> optionGroups = optionGroupRepository.findAll();
        List<OptionItem> optionItems = optionItemRepository.findAll();

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), questions,
                optionGroups, optionItems);

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest.answers(), structuredTemplate, reviewGroup);

        // then
        assertAll(
                () -> assertThat(review.getAnswersByType(TextAnswer.class))
                        .extracting(TextAnswer::getQuestionId)
                        .containsExactly(requiredTextQuestion.getId()),
                () -> assertThat(review.getAnswersByType(CheckboxAnswer.class))
                        .extracting(CheckboxAnswer::getQuestionId)
                        .containsExactly(requeiredCheckBoxQuestion.getId())
        );
    }
}
