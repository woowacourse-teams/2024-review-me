package reviewme.e2e;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewGroupFixture.리뷰그룹;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.dto.request.create.CreateReviewRequest2;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.Review2Repository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.E2ETest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.dto.response.TemplateResponse;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

class ReviewsE2ETest extends E2ETest {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private Question2Repository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private Review2Repository reviewRepository;

    @Autowired
    private CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    private TextAnswerRepository textAnswerRepository;

    Question2 카테고리_질문;
    OptionGroup 카테고리_옵션_그룹;
    OptionItem 카테고리_옵션_아이템1;
    OptionItem 카테고리_옵션_아이템2;

    Question2 꼬리질문_선택형_질문;
    OptionGroup 꼬리질문_옵션_그룹;
    OptionItem 꼬리질문_옵션_아이템1;
    OptionItem 꼬리질문_옵션_아이템2;
    Question2 꼬리질문_서술형_질문;

    @BeforeEach
    void saveTemplate() {
        // 카테고리 선택형 질문 저장
        카테고리_질문 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "카테고리 선택형", null, 1));
        카테고리_옵션_그룹 = optionGroupRepository.save(new OptionGroup(카테고리_질문.getId(), 1, 2));
        카테고리_옵션_아이템1 = optionItemRepository.save(new OptionItem("카테고리 선택지1", 카테고리_옵션_그룹.getId(), 1, OptionType.CATEGORY));
        카테고리_옵션_아이템2 = optionItemRepository.save(new OptionItem("카테고리 선택지2", 카테고리_옵션_그룹.getId(), 1, OptionType.CATEGORY));

        // 꼬리질문 선택형 질문 저장
        꼬리질문_선택형_질문 = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "꼬리질문 선택형", null, 1));
        꼬리질문_옵션_그룹 = optionGroupRepository.save(new OptionGroup(꼬리질문_선택형_질문.getId(), 1, 2));
        꼬리질문_옵션_아이템1 = optionItemRepository.save(new OptionItem("꼬리질문 선택지1", 꼬리질문_옵션_그룹.getId(), 1, OptionType.KEYWORD));
        꼬리질문_옵션_아이템2 = optionItemRepository.save(new OptionItem("꼬리질문 선택지2", 꼬리질문_옵션_그룹.getId(), 1, OptionType.KEYWORD));

        // 꼬리질문 서술형 질문 저장
        꼬리질문_서술형_질문 = questionRepository.save(new Question2(true, QuestionType.TEXT, "꼬리질문 서술형", null, 2));

        // 카테고리, 꼬리질문 섹션 저장
        Section categorySection = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(카테고리_질문.getId()), null, "헤더", 1));
        Section followingSection = sectionRepository.save(new Section(VisibleType.CONDITIONAL,
                List.of(꼬리질문_선택형_질문.getId(), 꼬리질문_서술형_질문.getId()), 1L, "헤더", 1));

        // 템플릿 저장
        templateRepository.save(new Template(List.of(categorySection.getId(), followingSection.getId())));
    }

    @Test
    void 리뷰_쓰기_위해_받아오는_정보_API() {
        // given
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰그룹);

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
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰그룹);
        CreateReviewAnswerRequest 카테고리_응답 = new CreateReviewAnswerRequest(카테고리_질문.getId(),
                List.of(카테고리_옵션_아이템1.getId()), null);
        CreateReviewAnswerRequest 꼬리질문_선택형_응답 = new CreateReviewAnswerRequest(꼬리질문_선택형_질문.getId(),
                List.of(꼬리질문_옵션_아이템2.getId()), null);
        CreateReviewAnswerRequest 꼬리질문_서술형_응답 = new CreateReviewAnswerRequest(꼬리질문_서술형_질문.getId(), null, "서술형응답");
        CreateReviewRequest2 request = new CreateReviewRequest2(savedReviewGroup.getReviewRequestCode(),
                List.of(카테고리_응답, 꼬리질문_선택형_응답, 꼬리질문_서술형_응답));

        // when, then
        RestAssured
                .given().log().all()
                .body(request)
                .contentType("application/json")
                .when().post("/v2/reviews")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
