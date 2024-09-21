package reviewme.sanchoTest;

import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지_키워드;
import static reviewme.fixture.OptionItemFixture.선택지_카테고리;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_옵션_질문;
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
import reviewme.review.service.module.ReviewListMapperDBVer;
import reviewme.review.service.module.ReviewListMapperStreamVer;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class PerformanceTest2 {

    @Autowired
    private ReviewListMapperDBVer reviewListMapperDBVer;

    @Autowired
    private ReviewListMapperStreamVer reviewListMapperStreamVer;

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

        Question question2 = questionRepository.save(선택형_필수_질문(n));
        questionIds.add(question2.getId());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 2));
        OptionItem optionItem3 = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 3));
        OptionItem optionItem4 = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 4));
        OptionItem optionItem5 = optionItemRepository.save(선택지_카테고리(optionGroup.getId(), 5));

        Question question3 = questionRepository.save(선택형_옵션_질문(n));
        questionIds.add(question3.getId());
        OptionGroup optionGroup6 = optionGroupRepository.save(선택지_그룹(question3.getId()));
        OptionItem optionItem6 = optionItemRepository.save(선택지_키워드(optionGroup6.getId(), 1));

        Question question4 = questionRepository.save(선택형_옵션_질문(n));
        questionIds.add(question4.getId());
        OptionGroup optionGroup7 = optionGroupRepository.save(선택지_그룹(question4.getId()));
        OptionItem optionItem7 = optionItemRepository.save(선택지_키워드(optionGroup7.getId(), 1));

        Question question6 = questionRepository.save(서술형_옵션_질문(n));
        questionIds.add(question6.getId());
        Question question7 = questionRepository.save(서술형_옵션_질문(n));
        questionIds.add(question7.getId());
        Question question8 = questionRepository.save(서술형_옵션_질문(n));
        questionIds.add(question8.getId());

        Section section = sectionRepository.save(항상_보이는_섹션(questionIds));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        List<TextAnswer> textAnswers = List.of(new TextAnswer(question6.getId(), "답변".repeat(20)),
                new TextAnswer(question7.getId(), "답변".repeat(20)),
                new TextAnswer(question8.getId(), "답변".repeat(20)));
        List<CheckboxAnswer> checkboxAnswers = List.of(
                new CheckboxAnswer(question2.getId(), List.of(optionItem1.getId(), optionItem2.getId())),
                new CheckboxAnswer(question3.getId(), List.of(optionItem6.getId())),
                new CheckboxAnswer(question4.getId(), List.of(optionItem7.getId()))
        );
        return reviewRepository.save(
                new Review(template.getId(), reviewGroupId, textAnswers, checkboxAnswers)
        );
    }


    @Test
    @DisplayName("성능 테스트")
    void teststr() {
        //  데이터 세팅
        ReviewGroup reviewGroup = saveReviewGroup();
        Review review = saveReview(reviewGroup.getId());

        // 캐싱
        reviewListMapperStreamVer.mapToReviewList(reviewGroup);

        long start1 = System.currentTimeMillis();
        reviewListMapperStreamVer.mapToReviewList(reviewGroup);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        reviewListMapperDBVer.mapToReviewList(reviewGroup);
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
        System.out.println("stream ver: " + (end1 - start1) + "ms");
        System.out.println("db ver: " + (end2 - start2) + "ms");

    }
}
