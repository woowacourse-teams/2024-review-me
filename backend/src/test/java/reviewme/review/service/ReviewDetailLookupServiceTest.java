package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
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
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.review.service.exception.ReviewNotFoundByIdAndCodesException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewDetailLookupServiceTest {

    @Autowired
    private ReviewDetailLookupService reviewDetailLookupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 잘못된_리뷰_확인_코드로_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        String groupAccessCode = "groupAccessCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", reviewRequestCode, groupAccessCode));

        Review review = reviewRepository.save(new Review(0, reviewGroup.getId(), List.of(), List.of()));

        // when, then
        assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                review.getId(), "wrong" + reviewRequestCode, groupAccessCode
        )).isInstanceOf(ReviewNotFoundByIdAndCodesException.class);
    }

    @Test
    void 잘못된_그룹_액세스_코드로_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        String groupAccessCode = "groupAccessCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", reviewRequestCode, groupAccessCode));

        Review review = reviewRepository.save(new Review(0, reviewGroup.getId(), List.of(), List.of()));

        // when, then
        assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                review.getId(), reviewRequestCode, "wrong" + groupAccessCode
        )).isInstanceOf(ReviewNotFoundByIdAndCodesException.class);
    }

    @Test
    void 리뷰_그룹에_해당하지_않는_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode1", "groupAccessCode1"));
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode2", "groupAccessCode2"));

        Review review1 = reviewRepository.save(new Review(0, reviewGroup1.getId(), List.of(), List.of()));
        Review review = reviewRepository.save(new Review(0, reviewGroup2.getId(), List.of(), List.of()));

        // when, then
        assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(
                review.getId(), reviewGroup1.getReviewRequestCode(), reviewGroup1.getGroupAccessCode()))
                .isInstanceOf(ReviewNotFoundByIdAndCodesException.class);
    }

    @Test
    void 사용자가_작성한_리뷰를_확인한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup("aru", "reviewme", "ABCD", "0000"));
        Question question1 = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", null, 1));
        Question question2 = questionRepository.save(new Question(true, QuestionType.CHECKBOX, "질문", null, 1));
        Question question3 = questionRepository.save(new Question(true, QuestionType.TEXT, "체크 1 조건", "가이드라인", 1));
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 3));
        OptionItem optionItem1 = optionItemRepository.save(
                new OptionItem("체크 1", optionGroup.getId(), 1, OptionType.KEYWORD));
        OptionItem optionItem2 = optionItemRepository.save(
                new OptionItem("체크 2", optionGroup.getId(), 1, OptionType.KEYWORD));

        Section section1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId(), question2.getId()), null, "1번 섹션", "말머리", 1)
        );
        Section section2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question3.getId()), optionItem1.getId(), "2번 섹션", "말머리", 2)
        );
        Template template = templateRepository.save(new Template(List.of(section1.getId(), section2.getId())));

        List<TextAnswer> textAnswers = List.of(
                new TextAnswer(1, "질문 1 답변 (20자 이상 입력 적용)"),
                new TextAnswer(3, "질문 3 답변 (20자 이상 입력 적용)")
        );
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(2, List.of(optionItem1.getId(), optionItem2.getId()))
        );
        Review review = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );

        // when
        TemplateAnswerResponse reviewDetail = reviewDetailLookupService.getReviewDetail(
                review.getId(), reviewGroup.getReviewRequestCode(), reviewGroup.getGroupAccessCode()
        );

        // then
        assertThat(reviewDetail.sections()).hasSize(2);
    }

    @Test
    void 답변이_있는_리뷰만_보여준다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup("aru", "reviewme", "ABCD", "0000"));
        Question question1 = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", null, 1));
        Question question2 = questionRepository.save(new Question(false, QuestionType.CHECKBOX, "질문", null, 1));
        Question question3 = questionRepository.save(new Question(true, QuestionType.TEXT, "체크 1 조건", "가이드라인", 1));
        Question question4 = questionRepository.save(new Question(false, QuestionType.TEXT, "선택 질문", "가이드라인", 1));
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 3));
        OptionItem optionItem1 = optionItemRepository.save(
                new OptionItem("체크 1", optionGroup.getId(), 1, OptionType.KEYWORD));
        OptionItem optionItem2 = optionItemRepository.save(
                new OptionItem("체크 2", optionGroup.getId(), 1, OptionType.KEYWORD));

        Section section1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId(), question2.getId()), null, "1번 섹션", "말머리", 1)
        );
        Section section2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question3.getId()), optionItem1.getId(), "2번 섹션", "말머리", 2)
        );
        Section section3 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question4.getId()), null, "3번 섹션", "말머리", 3)
        );

        Template template = templateRepository.save(
                new Template(List.of(section1.getId(), section2.getId(), section3.getId())));

        List<TextAnswer> textAnswers = List.of(
                new TextAnswer(1, "질문 1 답변"),
                new TextAnswer(3, "질문 3 답변")
        );
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        Review review = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );

        // when
        TemplateAnswerResponse reviewDetail = reviewDetailLookupService.getReviewDetail(
                review.getId(), reviewGroup.getReviewRequestCode(), reviewGroup.getGroupAccessCode()
        );

        // then
        List<SectionAnswerResponse> sections = reviewDetail.sections();

        assertAll(
                () -> assertThat(sections).extracting(SectionAnswerResponse::sectionId)
                        .containsExactly(section1.getId(), section2.getId()),
                () -> assertThat(sections.get(0).questions())
                        .extracting(QuestionAnswerResponse::questionId).containsExactly(question1.getId()),
                () -> assertThat(sections.get(1).questions())
                        .extracting(QuestionAnswerResponse::questionId).containsExactly(question3.getId())
        );
    }
}
