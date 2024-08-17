package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionItemFixture.꼬리_질문_선택지;
import static reviewme.fixture.QuestionFixture.꼬리_질문_서술형;
import static reviewme.fixture.QuestionFixture.꼬리_질문_선택형;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.꼬리_질문_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
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
    void 텍스트가_포함된_리뷰를_저장한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Question question1 = questionRepository.save(꼬리_질문_서술형.create());
        Question question2 = questionRepository.save(꼬리_질문_서술형.create());
        Section section = sectionRepository.save(
                꼬리_질문_섹션.createWithQuestionIds(List.of(question1.getId(), question2.getId()))
        );
        Template template = templateRepository.save(new Template(List.of(section.getId())));

        String content1 = "답변1";
        String content2 = "답변2";
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewGroup.getReviewRequestCode(),
                List.of(new CreateReviewAnswerRequest(question1.getId(), null, content1),
                        new CreateReviewAnswerRequest(question2.getId(), null, content2))
        );

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertAll(
                () -> assertThat(reviewRepository.findAll())
                        .extracting(Review::getReviewGroupId, Review::getTemplateId)
                        .containsExactlyInAnyOrder(
                                tuple(reviewGroup.getId(), template.getId())
                        ),
                () -> assertThat(textAnswerRepository.findAll())
                        .extracting(TextAnswer::getQuestionId, TextAnswer::getContent)
                        .containsExactlyInAnyOrder(
                                tuple(question1.getId(), content1),
                                tuple(question2.getId(), content2)
                        )
        );
    }

    @Test
    void 체크박스가_포함된_리뷰를_저장한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Question question = questionRepository.save(꼬리_질문_선택형.create());
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question.getId(), 2, 2));
        OptionItem optionItem1 = optionItemRepository.save(꼬리_질문_선택지.createWithOptionGroupId(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(꼬리_질문_선택지.createWithOptionGroupId(optionGroup.getId()));
        Section savedSection = sectionRepository.save(꼬리_질문_섹션.createWithQuestionIds(List.of(question.getId())));
        Template savedTemplate = templateRepository.save(new Template(List.of(savedSection.getId())));

        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewGroup.getReviewRequestCode(),
                List.of(new CreateReviewAnswerRequest(
                        question.getId(), List.of(optionItem1.getId(), optionItem2.getId()), null))
        );

        // when
        createReviewService.createReview(createReviewRequest);

        // then
        assertAll(
                () -> assertThat(reviewRepository.findAll())
                        .extracting(Review::getReviewGroupId, Review::getTemplateId)
                        .containsExactlyInAnyOrder(
                                tuple(reviewGroup.getId(), savedTemplate.getId())
                        ),
                () -> assertThat(checkboxAnswerRepository.findAll())
                        .flatExtracting(CheckboxAnswer::getSelectedOptionIds)
                        .extracting(CheckBoxAnswerSelectedOption::getSelectedOptionId)
                        .containsExactlyInAnyOrder(optionItem1.getId(), optionItem2.getId())
        );
    }
}
