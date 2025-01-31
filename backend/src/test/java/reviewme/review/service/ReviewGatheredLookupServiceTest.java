package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_질문;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.템플릿_지정_리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredByQuestionResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.TextResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewGatheredLookupServiceTest {

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewGatheredLookupService reviewLookupService;

    private ReviewGroup reviewGroup;

    @BeforeEach
    void saveReviewGroup() {
        reviewGroup = reviewGroupRepository.save(리뷰_그룹());
    }

    @Nested
    @DisplayName("섹션에 해당하는 서술형 응답을 질문별로 묶어 반환한다")
    class GatherAnswerByQuestionTest {

        @Test
        void 섹션_하위_질문이_여러개인_경우() {
            // given - 템플릿 저장
            Question question1 = 서술형_필수_질문();
            Question question2 = 서술형_필수_질문();
            Section section1 = 항상_보이는_섹션(List.of(question1, question2));
            Template template = templateRepository.save(new Template(List.of(section1)));

            // given - 리뷰 답변 저장
            TextAnswer answerAR1 = new TextAnswer(question1.getId(), "아루가 작성한 서술형 답변1");
            TextAnswer answerAR2 = new TextAnswer(question2.getId(), "아루가 작성한 서술형 답변2");
            TextAnswer answerTD1 = new TextAnswer(question1.getId(), "테드가 작성한 서술형 답변1");
            TextAnswer answerTD2 = new TextAnswer(question2.getId(), "테드가 작성한 서술형 답변2");
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerAR1, answerAR2)));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerTD1, answerTD2)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews().get(0).answers())
                    .extracting(TextResponse::content)
                    .containsExactlyInAnyOrder("아루가 작성한 서술형 답변1", "테드가 작성한 서술형 답변1");
            assertThat(actual.reviews().get(1).answers())
                    .extracting(TextResponse::content)
                    .containsExactlyInAnyOrder("아루가 작성한 서술형 답변2", "테드가 작성한 서술형 답변2");
        }

        @Test
        void 여러개의_섹션이_있는_경우_주어진_섹션ID에_해당하는_것만_반환한다() {
            // given - 템플릿 저장
            Question question1 = 서술형_필수_질문();
            Question question2 = 서술형_필수_질문();
            Section section1 = 항상_보이는_섹션(List.of(question1));
            Section section2 = 항상_보이는_섹션(List.of(question2));
            Template template = templateRepository.save(new Template(List.of(section1, section2)));

            // given - 리뷰 답변 저장
            TextAnswer answerAR1 = new TextAnswer(question1.getId(), "아루가 작성한 서술형 답변1");
            TextAnswer answerAR2 = new TextAnswer(question2.getId(), "아루가 작성한 서술형 답변2");
            TextAnswer answerTD1 = new TextAnswer(question1.getId(), "테드가 작성한 서술형 답변1");
            TextAnswer answerTD2 = new TextAnswer(question2.getId(), "테드가 작성한 서술형 답변2");
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerAR1, answerAR2)));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerTD1, answerTD2)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews().get(0).answers())
                    .extracting(TextResponse::content)
                    .containsExactlyInAnyOrder("아루가 작성한 서술형 답변1", "테드가 작성한 서술형 답변1");
        }

        @Test
        void 섹션에_필수가_아닌_질문이_있는_경우_답변된_내용만_반환한다() {
            // given - 템플릿 저장
            Question question1 = 서술형_필수_질문();
            Question question2 = 서술형_필수_질문();
            Section section1 = 항상_보이는_섹션(List.of(question1, question2));
            Template template = templateRepository.save(new Template(List.of(section1)));

            // given - 리뷰 답변 저장
            TextAnswer answerSC1 = new TextAnswer(question1.getId(), "산초가 작성한 서술형 답변1");
            TextAnswer answerSC2 = new TextAnswer(question2.getId(), "산초가 작성한 서술형 답변2");
            TextAnswer answerAR = new TextAnswer(question1.getId(), "아루가 작성한 서술형 답변");
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerSC1, answerSC2)));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answerAR)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews().get(0).answers())
                    .extracting(TextResponse::content)
                    .containsExactlyInAnyOrder("산초가 작성한 서술형 답변1", "아루가 작성한 서술형 답변");
            assertThat(actual.reviews().get(1).answers())
                    .extracting(TextResponse::content)
                    .containsExactly("산초가 작성한 서술형 답변2");
        }

        @Test
        void 질문에_응답이_없는_경우_질문_내용은_반환하되_응답은_빈_배열로_반환한다() {
            // given - 템플릿 저장
            Question question1 = 서술형_필수_질문();
            Section section1 = 항상_보이는_섹션(List.of(question1));
            templateRepository.save(new Template(List.of(section1)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews()).hasSize(1);
            assertThat(actual.reviews().get(0).question().name()).isEqualTo(question1.getContent());
            assertThat(actual.reviews().get(0).answers()).isEmpty();
            assertThat(actual.reviews().get(0).votes()).isNull();
        }
    }

    @Nested
    @DisplayName("섹션에 해당하는 선택형 응답을 질문별로 묶고, 선택된 횟수를 계산하여 반환한다")
    class GatherOptionAnswerByQuestionTest {

        @Test
        void 섹션_하위_질문이_하나인_경우() {
            // given - 템플릿 저장
            OptionItem optionItem1 = new OptionItem("짜장", 1, OptionType.CATEGORY);
            OptionItem optionItem2 = new OptionItem("짬뽕", 2, OptionType.CATEGORY);
            OptionGroup optionGroup = 선택지_그룹(List.of(optionItem1, optionItem2));
            Question question1 = new Question(true, QuestionType.CHECKBOX, optionGroup, "선택형 질문", null, 1);
            Section section1 = 항상_보이는_섹션(List.of(question1));
            Template template = templateRepository.save(new Template(List.of(section1)));

            // given - 리뷰 답변 저장
            CheckboxAnswer answer1 = new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId()));
            CheckboxAnswer answer2 = new CheckboxAnswer(
                    question1.getId(), List.of(optionItem1.getId(), optionItem2.getId())
            );
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answer1)));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answer2)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews().get(0).votes())
                    .extracting(VoteResponse::content, VoteResponse::count)
                    .containsExactlyInAnyOrder(tuple("짜장", 2L), tuple("짬뽕", 1L));
        }

        @Test
        void 아무도_고르지_않은_선택지는_0개로_계산하여_반환한다() {
            // given - 질문 저장
            OptionItem optionItem1 = new OptionItem("우테코 산초", 1, OptionType.CATEGORY);
            OptionItem optionItem2 = new OptionItem("제이든 산초", 2, OptionType.CATEGORY);
            OptionGroup optionGroup = 선택지_그룹(List.of(optionItem1, optionItem2));
            Question question1 = new Question(false, QuestionType.CHECKBOX, optionGroup, "선택형 질문", null, 1);

            // given - 섹션, 템플릿 저장
            Section section1 = 항상_보이는_섹션(List.of(question1));
            Template template = templateRepository.save(new Template(List.of(section1)));

            // given - 리뷰 답변 저장
            CheckboxAnswer answer1 = new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId()));
            CheckboxAnswer answer2 = new CheckboxAnswer(question1.getId(), List.of(optionItem1.getId()));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answer1)));
            reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answer2)));

            // when
            ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                    reviewGroup.getId(), section1.getId()
            );

            // then
            assertThat(actual.reviews().get(0).votes())
                    .extracting(VoteResponse::content, VoteResponse::count)
                    .containsExactlyInAnyOrder(tuple("우테코 산초", 2L), tuple("제이든 산초", 0L));
        }
    }

    @Test
    void 서술형_질문에_대한_응답과_선택형_질문에_대한_응답을_함께_반환한다() {
        // given - 질문 저장
        Question question1 = 서술형_필수_질문();
        Question question2 = 선택형_질문(true, 2, 2);
        List<OptionItem> optionItems = question2.getOptionGroup().getOptionItems();

        // given - 섹션, 템플릿 저장
        Section section1 = 항상_보이는_섹션(List.of(question1, question2));
        Template template = templateRepository.save(new Template(List.of(section1)));

        // given - 리뷰 답변 저장
        TextAnswer answer1 = new TextAnswer(question1.getId(), "아루가 작성한 서술형 답변");
        CheckboxAnswer answer2 = new CheckboxAnswer(
                question2.getId(),
                optionItems.stream().map(OptionItem::getId).toList() // check all options
        );
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(answer1, answer2)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                reviewGroup.getId(), section1.getId()
        );

        // then
        assertThat(actual.reviews()).hasSize(2);
        assertThat(actual.reviews())
                .extracting(ReviewsGatheredByQuestionResponse::question)
                .extracting(SimpleQuestionResponse::name)
                .containsOnly(question1.getContent(), question2.getContent());
        assertThat(actual.reviews().get(0).answers())
                .extracting(TextResponse::content)
                .containsExactly("아루가 작성한 서술형 답변");
        assertThat(actual.reviews().get(0).votes()).isNull();
        assertThat(actual.reviews().get(1).votes())
                .extracting(VoteResponse::content, VoteResponse::count)
                .containsExactlyInAnyOrder(
                        tuple(optionItems.get(0).getContent(), 1L),
                        tuple(optionItems.get(1).getContent(), 1L)
                );
        assertThat(actual.reviews().get(1).answers()).isNull();
    }

    @Test
    void 다른_사람이_받은_리뷰는_포함하지_않는다() {
        // given - 질문 저장
        Question question1 = 서술형_필수_질문();
        Section section1 = 항상_보이는_섹션(List.of(question1));
        Template template = templateRepository.save(new Template(List.of(section1)));

        ReviewGroup reviewGroupBE = 템플릿_지정_리뷰_그룹(template.getId());
        ReviewGroup reviewGroupFE = 템플릿_지정_리뷰_그룹(template.getId());
        reviewGroupRepository.saveAll(List.of(reviewGroupFE, reviewGroupBE));

        // given - 리뷰 답변 저장
        TextAnswer answerFE = new TextAnswer(question1.getId(), "프론트엔드가 작성한 서술형 답변");
        TextAnswer answerBE = new TextAnswer(question1.getId(), "백엔드가 작성한 서술형 답변");
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroupFE.getId(), List.of(answerFE)));
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroupBE.getId(), List.of(answerBE)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                reviewGroupBE.getId(), section1.getId());

        // then
        assertThat(actual.reviews()).hasSize(1);
    }

    @Test
    void 질문을_position순서대로_반환한다() {
        // given
        Question question1 = new Question(false, QuestionType.TEXT, "질문1", null, 3);
        Question question2 = new Question(false, QuestionType.TEXT, "질문2", null, 4);
        Question question3 = new Question(false, QuestionType.TEXT, "질문3", null, 1);
        Question question4 = new Question(false, QuestionType.TEXT, "질문4", null, 2);
        Section section1 = 항상_보이는_섹션(List.of(question1, question2, question3, question4));
        templateRepository.save(new Template(List.of(section1)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewLookupService.getReceivedReviewsBySectionId(
                reviewGroup.getId(), section1.getId()
        );

        // then
        assertThat(actual.reviews())
                .extracting(ReviewsGatheredByQuestionResponse::question)
                .extracting(SimpleQuestionResponse::name)
                .containsExactly(
                        question3.getContent(), question4.getContent(), question1.getContent(), question2.getContent()
                );
    }
}
