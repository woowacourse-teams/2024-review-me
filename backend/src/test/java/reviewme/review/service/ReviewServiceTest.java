package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.QuestionFixure.기술역량이_어떤가요;
import static reviewme.fixture.QuestionFixure.소프트스킬이_어떤가요;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.Review2;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.domain.exception.ReviewIsNotInReviewGroupException;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.QuestionSetupResponse;
import reviewme.review.dto.response.ReceivedReviewCategoryResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse2;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewSetupResponse;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.Review2Repository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
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
    ReviewRepository reviewRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    ReviewContentRepository reviewContentRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewKeywordRepository reviewKeywordRepository;

    @Autowired
    OptionItemRepository optionItemRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    @Autowired
    SectionRepository secionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    Review2Repository review2Repository;

    @Test
    void 리뷰를_생성한다() {
        // given
        Keyword keyword1 = keywordRepository.save(추진력이_좋아요.create());
        Keyword keyword2 = keywordRepository.save(회의를_이끌어요.create());
        Question question1 = questionRepository.save(소프트스킬이_어떤가요.create());
        Question question2 = questionRepository.save(기술역량이_어떤가요.create());

        String reviewRequestCode = "reviewRequestCode";
        reviewGroupRepository.save(new ReviewGroup("산초", "리뷰미 프로젝트", reviewRequestCode, "groupAccessCode"));

        CreateReviewContentRequest request1 = new CreateReviewContentRequest(question1.getId(), "답변".repeat(20));
        CreateReviewContentRequest request2 = new CreateReviewContentRequest(question2.getId(), "응답".repeat(20));
        CreateReviewRequest reviewRequest1 = new CreateReviewRequest(reviewRequestCode, List.of(request1, request2),
                List.of(keyword1.getId(), keyword2.getId()));
        CreateReviewRequest reviewRequest2 = new CreateReviewRequest(reviewRequestCode, List.of(request1),
                List.of(keyword1.getId()));

        // when
        long reviewId = reviewService.createReview(reviewRequest1);
        reviewService.createReview(reviewRequest2);

        // then
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        assertThat(optionalReview).isPresent();
        assertAll(
                () -> assertThat(optionalReview.get().getReviewContents()).hasSize(2),
                () -> assertThat(reviewKeywordRepository.findAllByReviewId(reviewId)).hasSize(2)
        );
    }

    @Test
    void 리뷰_작성을_위해_필요한_정보를_조회한다() {
        // given
        keywordRepository.save(추진력이_좋아요.create());
        keywordRepository.save(회의를_이끌어요.create());
        questionRepository.save(소프트스킬이_어떤가요.create());
        questionRepository.save(기술역량이_어떤가요.create());

        String reviewee = "테드";
        String projectName = "리뷰미 프로젝트";
        String reviewRequestCode = "reviewRequestCode";
        reviewGroupRepository.save(new ReviewGroup(reviewee, projectName, reviewRequestCode, "groupAccessCode"));

        // when
        ReviewSetupResponse reviewCreationSetup = reviewService.findReviewCreationSetup(reviewRequestCode);

        // then
        assertAll(
                () -> assertThat(reviewCreationSetup.revieweeName()).isEqualTo(reviewee),
                () -> assertThat(reviewCreationSetup.projectName()).isEqualTo(projectName),
                () -> assertThat(reviewCreationSetup.questions()).hasSize(2),
                () -> assertThat(reviewCreationSetup.keywords()).hasSize(2)
        );
    }

    @Test
    void 확인_코드에_해당하는_그룹이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewService.findReceivedReviews2("abc"))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_리뷰_리스트를_반환한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        Question question1 = questionRepository.save(new Question("프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)"));
        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question1.getId(), 1, 2));
        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2));

        Template template = templateRepository.save(new Template(List.of()));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", "reviewRequestCode", groupAccessCode)
        );
        CheckboxAnswer categoryAnswer1 = new CheckboxAnswer(question1.getId(), List.of(categoryOption1.getId()));
        CheckboxAnswer categoryAnswer2 = new CheckboxAnswer(question1.getId(), List.of(categoryOption2.getId()));
        Review2 review1 = new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer1), LocalDateTime.now());
        Review2 review2 = new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer2), LocalDateTime.now());
        review2Repository.saveAll(List.of(review1, review2));

        // when
        ReceivedReviewsResponse2 response = reviewService.findReceivedReviews2(groupAccessCode);

        // then
        assertThat(response.reviews()).hasSize(2);
    }

    @Test
    void 리뷰를_조회한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode", groupAccessCode));
        Review review = reviewRepository.save(new Review(reviewGroup.getId(), List.of(), LocalDateTime.now()));

        // when
        ReviewDetailResponse response = reviewService.findReceivedReviewDetail(groupAccessCode,
                review.getId());

        // then
        assertThat(response.id()).isEqualTo(review.getId());
    }

    @Test
    void 잘못된_그룹_액세스_코드로_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode", "groupAccessCode"));

        Review review = reviewRepository.save(new Review(reviewGroup.getId(), List.of(), LocalDateTime.now()));

        // when, then
        assertThatThrownBy(() -> reviewService.findReceivedReviewDetail("wrongGroupAccessCode", review.getId()))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 리뷰_그룹에_해당하는_않는_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode1", "groupAccessCode1"));
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode2", "groupAccessCode2"));

        Review review1 = reviewRepository.save(new Review(reviewGroup1.getId(), List.of(), LocalDateTime.now()));
        Review review2 = reviewRepository.save(new Review(reviewGroup2.getId(), List.of(), LocalDateTime.now()));

        // when, then
        assertThatThrownBy(
                () -> reviewService.findReceivedReviewDetail(reviewGroup1.getGroupAccessCode(), review2.getId()))
                .isInstanceOf(ReviewIsNotInReviewGroupException.class);
    }

    @Test
    void 리뷰_질문_내용에서_특정_문자열을_리뷰이의_이름으로_치환한다() {
        // given
        reviewGroupRepository.save(
                new ReviewGroup("에프이", "리뷰미 프로젝트", "ABCD1234", "1234ABCD")
        );
        questionRepository.save(new Question("{revieweeName}에게 응원의 메시지를 전해주세요."));

        // when
        ReviewSetupResponse response = reviewService.findReviewCreationSetup("ABCD1234");
        QuestionSetupResponse questionResponse = response.questions().get(0);

        // then
        assertThat(questionResponse.content()).contains("에프이");
    }

    @Test
    void 리뷰_목록을_반환할때_선택한_카테고리만_함께_반환한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        Question question1 = questionRepository.save(new Question("프로젝트 기간 동안, 팀원의 강점이 드러났던 순간을 선택해주세요. (1~2개)"));
        Question question2 = questionRepository.save(new Question("커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요. (1개 이상)"));

        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(question1.getId(), 1, 2));
        OptionGroup keywordOptionGroup = optionGroupRepository.save(new OptionGroup(question2.getId(), 1, 10));

        OptionItem categoryOption1 = new OptionItem("커뮤니케이션 능력 ", categoryOptionGroup.getId(), 1, OptionType.CATEGORY);
        OptionItem categoryOption2 = new OptionItem("시간 관리 능력", categoryOptionGroup.getId(), 2, OptionType.CATEGORY);
        OptionItem keywordOption = new OptionItem("얘기를 잘 들어줘요", keywordOptionGroup.getId(), 2, OptionType.KEYWORD);
        optionItemRepository.saveAll(List.of(categoryOption1, categoryOption2, keywordOption));

        Section section1 = secionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(question1.getId()), null, "팀원과 함께 한 기억을 떠올려볼게요.", 1)
        );
        Section section2 = secionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(question2.getId()), null, "선택한 순간들을 바탕으로 리뷰를 작성해볼게요.", 1)
        );
        Template template = templateRepository.save(new Template(List.of(section1.getId(), section2.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("커비", "리뷰미", "reviewRequestCode", groupAccessCode)
        );
        CheckboxAnswer categoryAnswer = new CheckboxAnswer(question1.getId(), List.of(categoryOption1.getId()));
        CheckboxAnswer keywordAnswer = new CheckboxAnswer(question2.getId(), List.of(keywordOption.getId()));
        review2Repository.save(
                new Review2(template.getId(), reviewGroup.getId(), List.of(), List.of(categoryAnswer, keywordAnswer),
                        LocalDateTime.now())
        );

        // when
        ReceivedReviewsResponse2 response = reviewService.findReceivedReviews2(groupAccessCode);

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
