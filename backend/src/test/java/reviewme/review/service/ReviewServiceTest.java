package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.OptionGroupFixture.카테고리_선택지_그룹;
import static reviewme.fixture.OptionItemFixture.카테고리_선택지;
import static reviewme.fixture.QuestionFixture.단점_보완_질문_서술형;
import static reviewme.fixture.QuestionFixture.카테고리_질문_선택형;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.단점_보완_섹션;
import static reviewme.fixture.SectionFixture.카테고리_섹션;

import java.util.List;
import org.assertj.core.groups.Tuple;
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
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 확인_코드에_해당하는_그룹이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewService.findReceivedReviews("abc"))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_리뷰_리스트를_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Question question = questionRepository.save(카테고리_질문_선택형.create());
        OptionGroup optionGroup = optionGroupRepository.save(카테고리_선택지_그룹.createWithQuestionId(question.getId()));
        OptionItem optionItem = optionItemRepository.save(카테고리_선택지.createWithOptionGroupId(optionGroup.getId()));
        Section section = sectionRepository.save(카테고리_섹션.createWithQuestionIds(List.of(question.getId())));
        Template template = templateRepository.save(new Template(List.of(section.getId())));

        Review review1 = new Review(template.getId(), reviewGroup.getId(), List.of(),
                List.of(new CheckboxAnswer(question.getId(), List.of(optionItem.getId())))
        );
        Review review2 = new Review(template.getId(), reviewGroup.getId(), List.of(),
                List.of(new CheckboxAnswer(question.getId(), List.of(optionItem.getId())))
        );
        reviewRepository.saveAll(List.of(review1, review2));

        // when
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(reviewGroup.getGroupAccessCode());

        // then
        assertThat(response.reviews()).hasSize(2);
    }

    @Test
    void 리뷰_목록을_반환할때_선택한_카테고리만_함께_반환한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Question categoryQuestion = questionRepository.save(카테고리_질문_선택형.create());
        OptionGroup categoryOptionGroup = optionGroupRepository.save(
                카테고리_선택지_그룹.createWithQuestionId(categoryQuestion.getId())
        );
        OptionItem categoryOptionItem1 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupId(categoryOptionGroup.getId())
        );
        OptionItem categoryOptionItem2 = optionItemRepository.save(
                카테고리_선택지.createWithOptionGroupId(categoryOptionGroup.getId())
        );
        Section categorySection = sectionRepository.save(
                카테고리_섹션.createWithQuestionIds(List.of(categoryQuestion.getId()))
        );
        Question textQuestion = questionRepository.save(단점_보완_질문_서술형.create());
        Section textSection = sectionRepository.save(단점_보완_섹션.createWithQuestionIds(List.of(textQuestion.getId())));
        Template template = templateRepository.save(
                new Template(List.of(categorySection.getId(), textSection.getId()))
        );

        Review review1 = reviewRepository.save(new Review(
                template.getId(), reviewGroup.getId(),
                List.of(new TextAnswer(textQuestion.getId(), "답변")),
                List.of(new CheckboxAnswer(categoryQuestion.getId(), List.of(categoryOptionItem1.getId())))
        ));
        Review review2 = reviewRepository.save(new Review(
                template.getId(), reviewGroup.getId(),
                List.of(new TextAnswer(textQuestion.getId(), "답변")),
                List.of(new CheckboxAnswer(categoryQuestion.getId(), List.of(categoryOptionItem2.getId())))
        ));

        // when
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(reviewGroup.getGroupAccessCode());

        // then
        assertThat(response.reviews())
                .extracting(
                        ReceivedReviewResponse::reviewId,
                        receivedReviewResponse -> receivedReviewResponse.categories()
                                .stream()
                                .map(ReceivedReviewCategoryResponse::optionId)
                                .toList())
                .containsExactlyInAnyOrder(
                        Tuple.tuple(review1.getId(), List.of(categoryOptionItem1.getId())),
                        Tuple.tuple(review2.getId(), List.of(categoryOptionItem2.getId()))
                );
    }
}
