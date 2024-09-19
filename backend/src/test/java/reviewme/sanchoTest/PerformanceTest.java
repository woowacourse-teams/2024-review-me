package reviewme.sanchoTest;

import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.module.ReviewDetailMapperOrigin;
import reviewme.review.service.module.ReviewDetailMapperTedVer;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class PerformanceTest {

    @Autowired
    private ReviewDetailMapperTedVer reviewDetailMapperTedVer;

    @Autowired
    private ReviewDetailMapperOrigin reviewDetailMapperOrigin;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("성능 테스트")
    void test() {
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 질문 저장
        Question question1 = questionRepository.save(선택형_필수_질문());
        Question question2 = questionRepository.save(서술형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId(), 2));

        // given - 섹션, 템플릿 저장
        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(question1.getId())));
        Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(question2.getId())));
        Template template = templateRepository.save(템플릿(List.of(section1.getId(), section2.getId())));

        // given - 리뷰 답변 저장
        List<TextAnswer> textAnswers = List.of(new TextAnswer(question2.getId(), "답변".repeat(20)));
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId(), optionItem2.getId()))
        );
        Review review = reviewRepository.save(
                new Review(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );

        int repeatTime = 10000;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < repeatTime; i++) {
            reviewDetailMapperTedVer.mapToReviewDetailResponse(review, reviewGroup);
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < repeatTime; i++) {
            reviewDetailMapperOrigin.mapToReviewDetailResponse(review, reviewGroup);
        }
        long end2 = System.currentTimeMillis();

        System.out.println();
        System.out.println("TedVer: " + (end1 - start1) + "ms");
        System.out.println("OriginVer: " + (end2 - start2) + "ms");
    }
}
