package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
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
    void 서술형_답변을_매핑한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(템플릿(List.of(section.getId())));

        String expectedTextAnswer = "답".repeat(20);
        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(), null, expectedTextAnswer);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(reviewAnswerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getAnswersByType(TextAnswer.class)).hasSize(1);
    }

    @Test
    void 선택형_답변을_매핑한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(템플릿(List.of(section.getId())));

        ReviewAnswerRequest reviewAnswerRequest = new ReviewAnswerRequest(question.getId(),
                List.of(optionItem1.getId()), null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(reviewGroup.getReviewRequestCode(),
                List.of(reviewAnswerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getAnswersByType(CheckboxAnswer.class)).hasSize(1);
    }

    @Test
    void 필수가_아닌_서술형_질문에_답변이_없으면_매핑하지_않는다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        Question question = questionRepository.save(서술형_옵션_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(템플릿(List.of(section.getId())));

        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(question.getId(), null, "");
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewGroup.getReviewRequestCode(), List.of(answerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getAnswersByType(TextAnswer.class))
                .extracting(TextAnswer::getQuestionId)
                .isEmpty();
    }

    @Test
    void 필수가_아닌_선택형_질문에_답변이_없으면_매핑하지_않는다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        Question question = questionRepository.save(선택형_옵션_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));

        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        templateRepository.save(템플릿(List.of(section.getId())));

        ReviewAnswerRequest answerRequest = new ReviewAnswerRequest(question.getId(), List.of(), null);
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewGroup.getReviewRequestCode(), List.of(answerRequest));

        // when
        Review review = reviewMapper.mapToReview(reviewRegisterRequest);

        // then
        assertThat(review.getAnswersByType(CheckboxAnswer.class))
                .extracting(CheckboxAnswer::getQuestionId)
                .isEmpty();
    }

    @Test
    void 잘못된_리뷰_요청_코드로_리뷰를_생성할_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "notExistCode";
        Question savedQuestion = questionRepository.save(서술형_필수_질문());
        ReviewAnswerRequest emptyTextReviewRequest = new ReviewAnswerRequest(
                savedQuestion.getId(), null, "");
        ReviewRegisterRequest reviewRegisterRequest = new ReviewRegisterRequest(
                reviewRequestCode, List.of(emptyTextReviewRequest));

        // when, then
        assertThatThrownBy(() -> reviewMapper.mapToReview(reviewRegisterRequest))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
