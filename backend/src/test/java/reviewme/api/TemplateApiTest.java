package reviewme.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;

class TemplateApiTest extends ApiTest {

    @Test
    void 리뷰_작성을_위한_템플릿을_반환한다() {
        BDDMockito.given(templateService.generateReviewForm(anyString()))
                .willReturn(TemplateFixture.templateResponse());

        ParameterDescriptor[] requestParameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("formId").description("폼 ID"),
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),

                fieldWithPath("sections[]").description("섹션 목록"),
                fieldWithPath("sections[].sectionId").description("섹션 ID"),
                fieldWithPath("sections[].visible").description("섹션 표시 여부 (반드시 보이거나, 조건부이거나)"),
                fieldWithPath("sections[].onSelectedOptionId").description("섹션이 보이기 위한 선택 항목 ID").optional(),
                fieldWithPath("sections[].header").description("섹션 제목"),

                fieldWithPath("sections[].questions[]").description("질문 목록"),
                fieldWithPath("sections[].questions[].questionId").description("질문 ID"),
                fieldWithPath("sections[].questions[].required").description("필수 여부"),
                fieldWithPath("sections[].questions[].content").description("질문 내용"),
                fieldWithPath("sections[].questions[].questionType").description("질문 타입"),
                fieldWithPath("sections[].questions[].hasGuideline").description("가이드라인 존재 여부"),
                fieldWithPath("sections[].questions[].guideline").description("가이드라인 내용").optional(),

                fieldWithPath("sections[].questions[].optionGroup").description("옵션 그룹").optional(),
                fieldWithPath("sections[].questions[].optionGroup.optionGroupId").description("옵션 그룹 ID"),
                fieldWithPath("sections[].questions[].optionGroup.minCount").description("최소 선택 개수"),
                fieldWithPath("sections[].questions[].optionGroup.maxCount").description("최대 선택 개수"),

                fieldWithPath("sections[].questions[].optionGroup.options[]").description("선택 항목 목록"),
                fieldWithPath("sections[].questions[].optionGroup.options[].optionId").description("선택 항목 ID"),
                fieldWithPath("sections[].questions[].optionGroup.options[].content").description("선택 항목 내용"),
        };

        RestDocumentationResultHandler handler = document(
                "get-review-form",
                queryParameters(requestParameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/reviews/write")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_그룹이_존재하지_않는_경우_예외를_반환한다() {
        BDDMockito.given(templateService.generateReviewForm(anyString()))
                .willThrow(new ReviewGroupNotFoundByReviewRequestCodeException(anyString()));

        ParameterDescriptor[] requestParameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        RestDocumentationResultHandler handler = document(
                "get-review-form-not-found",
                queryParameters(requestParameterDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/reviews/write")
                .then().log().all()
                .apply(handler)
                .statusCode(404);
    }
}
