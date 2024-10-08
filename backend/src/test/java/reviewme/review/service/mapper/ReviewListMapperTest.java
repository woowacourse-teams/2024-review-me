package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.OptionGroupFixture;
import reviewme.fixture.OptionItemFixture;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewListMapperTest {

    @Autowired
    private ReviewListMapper reviewListMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Test
    void 각_리뷰에_포함된_선택형_서술형_응답만을_반환한다() {
        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 질문 저장
        Question question = questionRepository.save(선택형_필수_질문());
        Question textQuestion = questionRepository.save(서술형_필수_질문());

        OptionGroup optionGroup = optionGroupRepository.save(
                OptionGroupFixture.선택지_그룹(question.getId()));

        long optionItem1 = optionItemRepository.save(OptionItemFixture.선택지(optionGroup.getId()))
                .getId();
        long optionItem2 = optionItemRepository.save(OptionItemFixture.선택지(optionGroup.getId()))
                .getId();
        long optionItem3 = optionItemRepository.save(OptionItemFixture.선택지(optionGroup.getId()))
                .getId();

        // 답변 저장
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer(
                question.getId(), List.of(optionItem1)
        );
        CheckboxAnswer checkboxAnswer2 = new CheckboxAnswer(
                question.getId(), List.of(optionItem2)
        );
        CheckboxAnswer checkboxAnswer3 = new CheckboxAnswer(
                question.getId(), List.of(optionItem3)
        );

        TextAnswer textAnswer1 = new TextAnswer(textQuestion.getId(), "텍스트형 응답1");
        TextAnswer textAnswer2 = new TextAnswer(textQuestion.getId(), "텍스트형 응답2");
        TextAnswer textAnswer3 = new TextAnswer(textQuestion.getId(), "텍스트형 응답3");

        // given - 섹션, 템플릿 저장
        Section categorySection = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(categorySection.getId())));

        // given - 리뷰 저장
        Review review1 = new Review(template.getId(), reviewGroup.getId(),
                List.of(textAnswer1, textAnswer2, checkboxAnswer1, checkboxAnswer2));
        Review review2 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer3, checkboxAnswer3));

        long lastReviewId = 8L;
        int size = 5;

        reviewRepository.saveAll(List.of(review1, review2));

        // when
        List<ReviewListElementResponse> responses = reviewListMapper.mapToReviewList(
                reviewGroup, lastReviewId, size);

        // then
        ReviewListElementResponse response1 = responses.get(1);
        ReviewListElementResponse response2 = responses.get(0);
        assertAll(
                () -> assertThat(response1.contentPreview()).contains("텍스트형 응답1"),
                () -> assertThat(response1.categories().stream().map(ReviewCategoryResponse::optionId).toList())
                        .containsExactly(optionItem1, optionItem2),
                () -> assertThat(response2.contentPreview()).contains("텍스트형 응답3"),
                () -> assertThat(response2.categories().stream().map(ReviewCategoryResponse::optionId).toList())
                        .containsExactly(optionItem3)
        );
    }

    @Test
    void 선택형_답변_중_카테고리_답변만_반환한다() {
        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 질문 저장
        Question categoryQuestion = questionRepository.save(선택형_필수_질문());
        Question nonCategoryQuestion = questionRepository.save(선택형_필수_질문());

        OptionGroup categoryOptionGroup = optionGroupRepository.save(
                OptionGroupFixture.선택지_그룹(categoryQuestion.getId()));
        OptionGroup nonCategoryOptionGroup = optionGroupRepository.save(
                OptionGroupFixture.선택지_그룹(nonCategoryQuestion.getId()));

        long categoryOptionItem1 = optionItemRepository.save(OptionItemFixture.선택지(categoryOptionGroup.getId()))
                .getId();
        long categoryOptionItem2 = optionItemRepository.save(OptionItemFixture.선택지(categoryOptionGroup.getId()))
                .getId();
        long nonCategoryOptionItem3 = optionItemRepository.save(new OptionItem(
                "비카테고리 옵션", nonCategoryOptionGroup.getId(), 1, OptionType.KEYWORD)).getId();

        // 답변 저장
        CheckboxAnswer categoryCheckboxAnswer1 = new CheckboxAnswer(
                categoryQuestion.getId(), List.of(categoryOptionItem1)
        );
        CheckboxAnswer categoryCheckboxAnswer2 = new CheckboxAnswer(
                categoryQuestion.getId(), List.of(categoryOptionItem2)
        );
        CheckboxAnswer nonCategoryCheckboxAnswer3 = new CheckboxAnswer(
                nonCategoryQuestion.getId(), List.of(nonCategoryOptionItem3)
        );

        // given - 섹션, 템플릿 저장
        Section categorySection = sectionRepository.save(항상_보이는_섹션(List.of(categoryQuestion.getId())));
        Section nonCategorySection = sectionRepository.save(조건부로_보이는_섹션(List.of(nonCategoryQuestion.getId()), 1));
        Template template = templateRepository.save(템플릿(List.of(categorySection.getId(), nonCategorySection.getId())));

        // given - 리뷰 저장
        reviewRepository.save(new Review(template.getId(), reviewGroup.getId(),
                List.of(categoryCheckboxAnswer1, categoryCheckboxAnswer2, nonCategoryCheckboxAnswer3))
        );

        long lastReviewId = 8L;
        int size = 5;

        // when
        List<ReviewListElementResponse> responses = reviewListMapper.mapToReviewList(reviewGroup, lastReviewId, size);

        // then
        List<Long> categoryOptionIds = responses.get(0).categories()
                .stream()
                .map(ReviewCategoryResponse::optionId)
                .toList();
        assertThat(categoryOptionIds).containsExactly(categoryOptionItem1, categoryOptionItem2)
                .doesNotContain(nonCategoryOptionItem3);
    }
}
