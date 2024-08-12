package reviewme.e2e;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.꼬리_질문_옵션_그룹;
import static reviewme.fixture.OptionGroupFixture.카테고리_옵션_그룹;
import static reviewme.fixture.OptionItemFixture.꼬리_질문_옵션_아이템1;
import static reviewme.fixture.OptionItemFixture.꼬리_질문_옵션_아이템2;
import static reviewme.fixture.OptionItemFixture.카테고리_옵션_아이템1;
import static reviewme.fixture.OptionItemFixture.카테고리_옵션_아이템2;
import static reviewme.fixture.Question2Fixture.객관식_꼬리_질문;
import static reviewme.fixture.Question2Fixture.객관식_카테고리_질문;
import static reviewme.fixture.Question2Fixture.주관식_꼬리_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.꼬리_질문_섹션;
import static reviewme.fixture.SectionFixture.카테고리_섹션;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.TextAnswer;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.dto.request.create.CreateReviewRequest2;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.Review2Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.E2ETest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.dto.response.TemplateResponse;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

class ReviewsE2ETest extends E2ETest {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private QuestionRepository2 questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private Review2Repository reviewRepository;

    Question2 savedCategoryQuestion;
    OptionGroup savedCategoryOptionGroup;
    OptionItem savedCategoryOptionItem1;
    OptionItem savedCategoryOptionItem2;

    Question2 savedFollowingCheckboxQuestion;
    OptionGroup savedFollowingOptionGroup;
    OptionItem savedFollowingOptionItem1;
    OptionItem savedFollowingOptionItem2;
    Question2 savedFollowingTextQuestion;

    Section savedCategorySection;
    Section savedFollowingSection;

    @BeforeEach
    void saveTemplate() {
        savedCategoryQuestion = questionRepository.save(객관식_카테고리_질문.create());
        savedCategoryOptionGroup = optionGroupRepository.save(카테고리_옵션_그룹.create(savedCategoryQuestion.getId()));
        savedCategoryOptionItem1 = optionItemRepository.save(카테고리_옵션_아이템1.create(savedCategoryOptionGroup.getId()));
        savedCategoryOptionItem2 = optionItemRepository.save(카테고리_옵션_아이템2.create(savedCategoryOptionGroup.getId()));

        savedFollowingCheckboxQuestion = questionRepository.save(객관식_꼬리_질문.create());
        savedFollowingOptionGroup = optionGroupRepository.save(꼬리_질문_옵션_그룹.create(savedFollowingCheckboxQuestion.getId()));
        savedFollowingOptionItem1 = optionItemRepository.save(꼬리_질문_옵션_아이템1.create(savedFollowingOptionGroup.getId()));
        savedFollowingOptionItem2 = optionItemRepository.save(꼬리_질문_옵션_아이템2.create(savedFollowingOptionGroup.getId()));
        savedFollowingTextQuestion = questionRepository.save(주관식_꼬리_질문.create());

        savedCategorySection = sectionRepository.save(카테고리_섹션.create(List.of(savedCategoryQuestion.getId())));
        savedFollowingSection = sectionRepository.save(꼬리_질문_섹션.create(List.of(savedFollowingCheckboxQuestion.getId(), savedFollowingTextQuestion.getId())));

        templateRepository.save(new Template(List.of(savedCategorySection.getId(), savedFollowingSection.getId())));
    }

    @Test
    void 리뷰_쓰기_위해_받아오는_정보_API() {
        // given
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹.create());

        // when
        TemplateResponse response = RestAssured
                .given().log().all()
                .when().get("/v2/reviews/write?reviewRequestCode=" + savedReviewGroup.getReviewRequestCode())
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(TemplateResponse.class);

        // then
        assertAll(
                () -> assertThat(response.revieweeName()).isEqualTo(savedReviewGroup.getReviewee()),
                () -> assertThat(response.projectName()).isEqualTo(savedReviewGroup.getProjectName()),
                () -> assertThat(response.sections()).isNotNull(),
                () -> assertThat(response.sections().get(0).questions()).isNotNull()
        );
    }

    @Test
    void 리뷰_쓰기_API() {
        // given
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        CreateReviewAnswerRequest categoryAnswer = new CreateReviewAnswerRequest(
                savedCategoryQuestion.getId(),
                List.of(savedCategoryOptionItem1.getId()),
                null
        );
        CreateReviewAnswerRequest followingCheckboxAnswer = new CreateReviewAnswerRequest(
                savedFollowingCheckboxQuestion.getId(),
                List.of(savedFollowingOptionItem2.getId()),
                null
        );
        CreateReviewAnswerRequest followingTextAnswer = new CreateReviewAnswerRequest(
                savedFollowingTextQuestion.getId(),
                null,
                "서술형응답"
        );
        CreateReviewRequest2 request = new CreateReviewRequest2(
                savedReviewGroup.getReviewRequestCode(),
                List.of(categoryAnswer, followingCheckboxAnswer, followingTextAnswer)
        );

        // when, then
        RestAssured
                .given().log().all()
                .body(request)
                .contentType("application/json")
                .when().post("/v2/reviews")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 내가_받은_리뷰_목록_조회_API() {
        // given
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        reviewRepository.save(
                new Review2(
                        1L,
                        savedReviewGroup.getId(),
                        List.of(new TextAnswer(savedFollowingTextQuestion.getId(), "서술형응답")),
                        List.of(new CheckboxAnswer(savedCategoryQuestion.getId(),
                                        List.of(savedCategoryOptionItem1.getId())),
                                new CheckboxAnswer(savedFollowingCheckboxQuestion.getId(),
                                        List.of(savedFollowingOptionItem1.getId()))
                        )
                )
        );

        // when
        ReceivedReviewsResponse response = RestAssured
                .given().log().all()
                .header("GroupAccessCode", savedReviewGroup.getGroupAccessCode())
                .when().get("/reviews")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ReceivedReviewsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.projectName()).isEqualTo(savedReviewGroup.getProjectName()),
                () -> assertThat(response.revieweeName()).isEqualTo(savedReviewGroup.getReviewee()),
                () -> assertThat(response.reviews()).isNotNull()
        );
    }
}
