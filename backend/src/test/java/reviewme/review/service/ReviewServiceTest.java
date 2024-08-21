package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.exception.ReviewGroupUnauthorizedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    OptionItemRepository optionItemRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void 리뷰_요청_코드가_존재하지_않는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewService.findReceivedReviews("abc", "groupAccessCode"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }

    @Test
    void 그룹_액세스_코드가_일치하지_않는_경우_예외가_발생한다() {
        // given
        String reviewRequestCode = "code";
        String groupAccessCode = "1234";
        reviewGroupRepository.save(new ReviewGroup("커비", "리뷰미", reviewRequestCode, groupAccessCode));

        // when, then
        assertThatThrownBy(() -> reviewService.findReceivedReviews(reviewRequestCode, "5678"))
                .isInstanceOf(ReviewGroupUnauthorizedException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_리뷰_리스트를_반환한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        String groupAccessCode = "groupAccessCode";
        Question question = questionRepository.save(
                new Question(true, QuestionType.CHECKBOX, "프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)", null, 1)
        );
        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question.getId(), 1, 2));
        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2));

        Template template = templateRepository.save(new Template(List.of()));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", reviewRequestCode, groupAccessCode)
        );
        CheckboxAnswer categoryAnswer1 = new CheckboxAnswer(question.getId(), List.of(categoryOption1.getId()));
        CheckboxAnswer categoryAnswer2 = new CheckboxAnswer(question.getId(), List.of(categoryOption2.getId()));
        Review review1 = new Review(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer1));
        Review review = new Review(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer2));
        reviewRepository.saveAll(List.of(review1, review));

        // when
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(reviewRequestCode, groupAccessCode);

        // then
        assertThat(response.reviews()).hasSize(2);
    }
}
