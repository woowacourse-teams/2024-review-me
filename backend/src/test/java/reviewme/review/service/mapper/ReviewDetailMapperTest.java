package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.cache.TemplateCache;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewJpaRepository;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateJpaRepository;

@ServiceTest
class ReviewDetailMapperTest {

    @Autowired
    private ReviewDetailMapper reviewDetailMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateJpaRepository templateJpaRepository;

    @Autowired
    private ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private TemplateCache templateCache;

    @Test
    void 리뷰에_포함된_섹션_응답만을_반환한다() {
        // given - 질문 저장
        Question checkBoxquestion = questionRepository.save(선택형_필수_질문());
        Question textQuestion1 = questionRepository.save(서술형_필수_질문());
        Question textQuestion2 = questionRepository.save(서술형_필수_질문());

        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(checkBoxquestion.getId()));
        long optionItem = optionItemRepository.save(선택지(optionGroup.getId())).getId();

        // given - 섹션, 템플릿 저장
        Section section1 = sectionRepository.save(
                조건부로_보이는_섹션(List.of(checkBoxquestion.getId(), textQuestion1.getId()), 1)
        );
        Section section2 = sectionRepository.save(조건부로_보이는_섹션(List.of(textQuestion2.getId()), 1));
        Template template = templateJpaRepository.save(템플릿(List.of(section1.getId(), section2.getId())));
        templateCache.init();

        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // 답변 저장
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkBoxquestion.getId(), List.of(optionItem));
        TextAnswer textAnswer1 = new TextAnswer(textQuestion1.getId(), "텍스트형 응답");
        TextAnswer textAnswer2 = new TextAnswer(textQuestion2.getId(), "텍스트형 응답");

        // given - 리뷰 저장
        Review review1 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer1, checkboxAnswer));
        Review review2 = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer2));
        reviewJpaRepository.saveAll(List.of(review1, review2));

        // when
        ReviewDetailResponse response = reviewDetailMapper.mapToReviewDetailResponse(review1, reviewGroup);

        // then
        List<Long> sectionIds = response.sections().stream().map(SectionAnswerResponse::sectionId).toList();
        assertThat(sectionIds).containsExactly(section1.getId());
    }

    @Test
    void 섹션에_포함된_질문_응답만을_반환한다() {
        // given - 질문 저장
        Question checkBoxquestion = questionRepository.save(선택형_필수_질문());
        Question textQuestion1 = questionRepository.save(서술형_필수_질문());
        Question textQuestion2 = questionRepository.save(서술형_필수_질문());

        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(checkBoxquestion.getId()));
        long optionItemId = optionItemRepository.save(선택지(optionGroup.getId())).getId();

        // given - 섹션, 템플릿 저장
        Section section1 = sectionRepository.save(
                조건부로_보이는_섹션(List.of(checkBoxquestion.getId(), textQuestion1.getId()), 1)
        );
        Section section2 = sectionRepository.save(조건부로_보이는_섹션(List.of(textQuestion2.getId()), 1));
        Template template = templateJpaRepository.save(템플릿(List.of(section1.getId(), section2.getId())));
        templateCache.init();

        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // 답변 저장
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(checkBoxquestion.getId(), List.of(optionItemId));
        TextAnswer textAnswer1 = new TextAnswer(textQuestion1.getId(), "텍스트형 응답");
        TextAnswer textAnswer2 = new TextAnswer(textQuestion2.getId(), "텍스트형 응답");

        // given - 리뷰 저장
        Review review1 = reviewJpaRepository.save(
                new Review(template.getId(), reviewGroup.getId(),
                        List.of(textAnswer1, textAnswer2, checkboxAnswer)));

        // when
        ReviewDetailResponse response = reviewDetailMapper.mapToReviewDetailResponse(review1, reviewGroup);

        // then
        List<Long> questionIds = response.sections().get(0).questions()
                .stream()
                .map(QuestionAnswerResponse::questionId)
                .toList();
        assertThat(questionIds).containsExactly(checkBoxquestion.getId(), textQuestion1.getId());
    }


    @Test
    void 질문에_포함된_선택지_응답만을_반환한다() {
        // given - 질문 저장
        Question checkBoxQuestion1 = questionRepository.save(선택형_필수_질문());
        Question checkBoxQuestion2 = questionRepository.save(선택형_필수_질문());

        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(checkBoxQuestion1.getId()));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(checkBoxQuestion2.getId()));
        long optionItemId1 = optionItemRepository.save(선택지(optionGroup1.getId())).getId();
        long optionItemId2 = optionItemRepository.save(선택지(optionGroup2.getId())).getId();

        // given - 섹션, 템플릿 저장
        Section section1 = sectionRepository.save(
                조건부로_보이는_섹션(List.of(checkBoxQuestion1.getId(), checkBoxQuestion2.getId()), 1)
        );
        Template template = templateJpaRepository.save(템플릿(List.of(section1.getId())));
        templateCache.init();

        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // 답변 저장
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer(checkBoxQuestion1.getId(), List.of(optionItemId1));
        CheckboxAnswer checkboxAnswer2 = new CheckboxAnswer(checkBoxQuestion2.getId(), List.of(optionItemId2));

        // given - 리뷰 저장
        Review review1 = reviewJpaRepository.save(
                new Review(template.getId(), reviewGroup.getId(), List.of(checkboxAnswer1, checkboxAnswer2)));

        // when
        ReviewDetailResponse response = reviewDetailMapper.mapToReviewDetailResponse(review1, reviewGroup);

        // then
        List<Long> optionIds = response.sections().get(0).questions().get(0).optionGroup().options()
                .stream()
                .map(OptionItemAnswerResponse::optionId)
                .toList();
        assertThat(optionIds).containsExactly(optionItemId1);
    }
}

