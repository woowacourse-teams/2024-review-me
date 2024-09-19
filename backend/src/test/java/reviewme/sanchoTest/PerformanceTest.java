package reviewme.sanchoTest;

import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private Random rnd = new Random();

    private ReviewGroup saveReviewGroup() {
        int n = rnd.nextInt(0, Integer.MAX_VALUE);
        ReviewGroup reviewGroup = new ReviewGroup(String.valueOf(n), String.valueOf(n),
                String.valueOf(n), String.valueOf(n));

        return reviewGroupRepository.save(reviewGroup);
    }

    private Review saveReview(long reviewGroupId) {
        List<Long> questionIds = new ArrayList<>();
        int n = rnd.nextInt(0, Integer.MAX_VALUE);

        Question question1 = questionRepository.save(서술형_옵션_질문(n));
        questionIds.add(question1.getId());

        Question question2 = questionRepository.save(선택형_필수_질문(n));
        questionIds.add(question2.getId());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId(), 2));

        Section section = sectionRepository.save(항상_보이는_섹션(questionIds));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        List<TextAnswer> textAnswers = List.of(new TextAnswer(question1.getId(), "답변".repeat(20)));
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question2.getId(), List.of(optionItem1.getId(), optionItem2.getId()))
        );
        return reviewRepository.save(
                new Review(template.getId(), reviewGroupId, textAnswers, checkboxAnswers)
        );
    }

    @Test
    @DisplayName("성능 테스트")
    void test() {
        int repeatTime = 10000;

        // 10000개의 데이터 세팅
        ReviewGroup reviewGroup = saveReviewGroup();
        Review review = saveReview(reviewGroup.getId());
        for (int i = 0; i < repeatTime; i++) {
            ReviewGroup tmpReviewGroup = saveReviewGroup();
            saveReview(tmpReviewGroup.getId());
        }

        // 10000번 반복
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
        long reviewGroupCount = reviewGroupRepository.count();
        long questionCount = questionRepository.count();
        long optionGroupCount = optionGroupRepository.count();
        long optionItemCount = optionItemRepository.count();
        long sectionCount = sectionRepository.count();
        long templateCount = templateRepository.count();
        long reviewCount = reviewRepository.count();

        System.out.println("리뷰 그룹 개수: " + reviewGroupCount);
        System.out.println("질문 개수: " + questionCount);
        System.out.println("옵션 그룹 개수: " + optionGroupCount);
        System.out.println("옵션 아이템 개수: " + optionItemCount);
        System.out.println("섹션 개수: " + sectionCount);
        System.out.println("템플릿 개수: " + templateCount);
        System.out.println("리뷰 개수: " + reviewCount);
        System.out.println();
        System.out.println("TedVer: " + (end1 - start1) + "ms");
        System.out.println("OriginVer: " + (end2 - start2) + "ms");
    }
}
