package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지_카테고리;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.fixture.OptionItemFixture;
import reviewme.question.domain.OptionItem;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Test
    void 리뷰_아이디로_선택한_옵션_아이템_아이디를_불러온다() {
        // given
        long questionId = questionRepository.save(선택형_필수_질문()).getId();

        Long optionGroupId = optionGroupRepository.save(선택지_그룹(questionId)).getId();
        long optionId1 = optionItemRepository.save(OptionItemFixture.선택지_카테고리(optionGroupId)).getId();
        long optionId2 = optionItemRepository.save(OptionItemFixture.선택지_카테고리(optionGroupId)).getId();
        long optionId3 = optionItemRepository.save(OptionItemFixture.선택지_카테고리(optionGroupId)).getId();
        long optionId4 = optionItemRepository.save(OptionItemFixture.선택지_카테고리(optionGroupId)).getId();
        optionItemRepository.save(OptionItemFixture.선택지_카테고리(optionGroupId));

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(questionId, List.of(optionId1, optionId2)),
                new CheckboxAnswer(questionId, List.of(optionId3, optionId4))
        );
        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        Set<Long> actual = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionId1, optionId2, optionId3, optionId4);
    }

    @Test
    void 리뷰_아이디와_질문_아이디로_선택한_옵션_아이템을_순서대로_불러온다() {
        // given
        long questionId1 = questionRepository.save(선택형_필수_질문()).getId();
        long questionId2 = questionRepository.save(선택형_필수_질문()).getId();

        long optionGroupId = optionGroupRepository.save(선택지_그룹(questionId1)).getId();
        long optionId1 = optionItemRepository.save(선택지_카테고리(optionGroupId, 3)).getId();
        long optionId2 = optionItemRepository.save(선택지_카테고리(optionGroupId, 2)).getId();
        long optionId3 = optionItemRepository.save(선택지_카테고리(optionGroupId, 1)).getId();
        long optionId4 = optionItemRepository.save(선택지_카테고리(optionGroupId, 1)).getId();
        long optionId5 = optionItemRepository.save(선택지_카테고리(optionGroupId, 1)).getId();

        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(questionId1, List.of(optionId1, optionId3)),
                new CheckboxAnswer(questionId2, List.of(optionId4))
        );

        Review review = reviewRepository.save(new Review(0, 0, List.of(), checkboxAnswers));

        // when
        List<OptionItem> actual = optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(
                review.getId(), questionId1
        );

        // then
        assertThat(actual).extracting(OptionItem::getId).containsExactly(optionId3, optionId1);
    }
}
