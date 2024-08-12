package reviewme.e2e;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @BeforeEach
    void saveTemplate() {
        // 카테고리 선택형 질문 저장
        Question2 categoryQuestion = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "카테고리 선택형", null, 1));
        OptionGroup categoryOptionGroup = optionGroupRepository.save(new OptionGroup(categoryQuestion.getId(), 1, 2));
        OptionItem categoryOptionItem1 = optionItemRepository.save(new OptionItem("카테고리 선택지1", categoryOptionGroup.getId(), 1, OptionType.CATEGORY));
        OptionItem categoryOptionItem2 = optionItemRepository.save(new OptionItem("카테고리 선택지2", categoryOptionGroup.getId(), 1, OptionType.CATEGORY));

        // 꼬리질문 선택형 질문 저장
        Question2 followingCheckBoxQuestion = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "꼬리질문 선택형", null, 1));
        OptionGroup followingCheckBoxOptionGroup = optionGroupRepository.save(new OptionGroup(followingCheckBoxQuestion.getId(), 1, 2));
        OptionItem followingCheckBoxOptionItem1 = optionItemRepository.save(new OptionItem("꼬리질문 선택지1", followingCheckBoxOptionGroup.getId(), 1, OptionType.KEYWORD));
        OptionItem followingCheckBoxOptionItem2 = optionItemRepository.save(new OptionItem("꼬리질문 선택지2", followingCheckBoxOptionGroup.getId(), 1, OptionType.KEYWORD));

        // 꼬리질문 서술형 질문 저장
        Question2 followingTextQuestion = questionRepository.save(new Question2(true, QuestionType.TEXT, "꼬리질문 서술형", null, 2));

        // 카테고리, 꼬리질문 섹션 저장
        Section categorySection = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(categoryQuestion.getId()), null, "헤더", 1));
        Section followingSection = sectionRepository.save(new Section(VisibleType.CONDITIONAL,
                List.of(followingCheckBoxQuestion.getId(), followingCheckBoxQuestion.getId()), 1L, "헤더", 1));

        // 템플릿 저장
        templateRepository.save(new Template(List.of(categorySection.getId(), followingSection.getId())));
    }

    @Test
    void 리뷰_쓰기_위해_받아오는_정보_API() {
        // given
        ReviewGroup savedReviewGroup = reviewGroupRepository.save(
                new ReviewGroup("revieweeName", "projectName", "reviewRequestCode", "groupAccessCode"));

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
}
