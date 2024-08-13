package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.dto.response.ReceivedReviewCategoryResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse2;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.Review2Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    QuestionRepository2 questionRepository;

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
    Review2Repository review2Repository;

    @Test
    void 확인_코드에_해당하는_그룹이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewService.findReceivedReviews("abc"))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_리뷰_리스트를_반환한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        Question2 question = questionRepository.save(
                new Question2(true, QuestionType.CHECKBOX, "프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)", null, 1)
        );
        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question.getId(), 1, 2));
        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2));

        Template template = templateRepository.save(new Template(List.of()));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", "reviewRequestCode", groupAccessCode)
        );
        CheckboxAnswer categoryAnswer1 = new CheckboxAnswer(question.getId(), List.of(categoryOption1.getId()));
        CheckboxAnswer categoryAnswer2 = new CheckboxAnswer(question.getId(), List.of(categoryOption2.getId()));
        Review2 review1 = new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer1));
        Review2 review2 = new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer2));
        review2Repository.saveAll(List.of(review1, review2));

        // when
        ReceivedReviewsResponse2 response = reviewService.findReceivedReviews(groupAccessCode);

        // then
        assertThat(response.reviews()).hasSize(2);
    }

    @Test
    void 리뷰_목록을_반환할때_선택한_카테고리만_함께_반환한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        Question2 question1 = questionRepository.save(
                new Question2(true, QuestionType.CHECKBOX, "프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)", null, 1)
        );
        Question2 question2 = questionRepository.save(
                new Question2(true, QuestionType.CHECKBOX, "커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요. (1개 이상)", null, 2)
        );

        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question1.getId(), 1, 2));
        OptionGroup keywordOptionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 10));

        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        OptionItem keywordOption = new OptionItem("얘기를 잘 들어줘요", keywordOptionGroup.getId(), 2, OptionType.KEYWORD);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2, keywordOption));

        Section section1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId()), null, "팀원과 함께 한 기억을 떠올려볼게요.", 1)
        );
        Section section2 = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question2.getId()), null, "선택한 순간들을 바탕으로 리뷰를 작성해볼게요.", 1)
        );
        Template template = templateRepository.save(new Template(List.of(section1.getId(), section2.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", "reviewRequestCode", groupAccessCode)
        );
        CheckboxAnswer categoryAnswer = new CheckboxAnswer(question1.getId(), List.of(categoryOption1.getId()));
        CheckboxAnswer keywordAnswer = new CheckboxAnswer(question2.getId(), List.of(keywordOption.getId()));
        review2Repository.save(
                new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer, keywordAnswer))
        );

        // when
        ReceivedReviewsResponse2 response = reviewService.findReceivedReviews(groupAccessCode);

        // then
        List<String> categoryContents = optionItemRepository.findAllByOptionType(OptionType.CATEGORY)
                .stream()
                .map(OptionItem::getContent)
                .toList();

        assertThat(response.reviews())
                .map(review -> review.categories()
                        .stream()
                        .map(ReceivedReviewCategoryResponse::content)
                        .toList())
                .allSatisfy(content -> assertThat(categoryContents).containsAll(content));
    }
}
