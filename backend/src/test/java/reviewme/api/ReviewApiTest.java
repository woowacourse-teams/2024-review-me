package reviewme.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.review.service.dto.request.CreateReviewRequest;

class ReviewApiTest extends ApiTest {

    private final String request = """
            {
                "reviewRequestCode": "ABCD1234",
                "answers": [
                    {
                        "questionId": 1,
                        "selectedOptionIds": [1, 2]
                    },
                    {
                        "questionId": 2,
                        "text": "답변 예시 1"
                    }
                ]
            }
            """;

    @Test
    void 리뷰를_등록한다() {
        BDDMockito.given(createReviewService.createReview(any(CreateReviewRequest.class)))
                .willReturn(1L);

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("텍스트 답변").optional()
        };

        RestDocumentationResultHandler handler = document(
                "create-review",
                requestFields(requestFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/reviews")
                .then().log().all()
                .apply(handler)
                .statusCode(201);
    }

    @Test
    void 리뷰_그룹_코드가_올바르지_않은_경우_예외가_발생한다() {
        BDDMockito.given(createReviewService.createReview(any(CreateReviewRequest.class)))
                .willThrow(new ReviewGroupNotFoundByRequestReviewCodeException(anyString()));

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("answers[].questionId").description("질문 ID"),
                fieldWithPath("answers[].selectedOptionIds").description("선택한 옵션 ID 목록").optional(),
                fieldWithPath("answers[].text").description("텍스트 답변").optional()
        };

        RestDocumentationResultHandler handler = document(
                "create-review-invalid-review-request-code",
                requestFields(requestFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/reviews")
                .then().log().all()
                .apply(handler)
                .statusCode(404);
    }

    @Test
    void 자신이_받은_리뷰_한_개를_조회한다() {
        BDDMockito.given(reviewDetailLookupService.getReviewDetail(anyString(), anyLong()))
                .willReturn(TemplateFixture.templateAnswerResponse());

        HeaderDescriptor[] requestHeaderDescriptors = {
                headerWithName("groupAccessCode").description("그룹 접근 코드")
        };

        ParameterDescriptor[] requestPathDescriptors = {
                parameterWithName("id").description("리뷰 ID")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("createdAt").description("리뷰 생성 날짜"),
                fieldWithPath("formId").description("폼 ID"),
                fieldWithPath("revieweeName").description("리뷰어 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),

                fieldWithPath("sections[].sectionId").description("섹션 ID"),
                fieldWithPath("sections[].header").description("섹션 제목"),

                fieldWithPath("sections[].questions[].questionId").description("질문 ID"),
                fieldWithPath("sections[].questions[].required").description("필수 여부"),
                fieldWithPath("sections[].questions[].content").description("질문 내용"),
                fieldWithPath("sections[].questions[].questionType").description("질문 타입"),
                fieldWithPath("sections[].questions[].optionGroup").description("옵션 그룹").optional(),

                fieldWithPath("sections[].questions[].optionGroup.optionGroupId").description("옵션 그룹 ID"),
                fieldWithPath("sections[].questions[].optionGroup.minCount").description("최소 선택 개수"),
                fieldWithPath("sections[].questions[].optionGroup.maxCount").description("최대 선택 개수"),

                fieldWithPath("sections[].questions[].optionGroup.options[].optionId").description("선택 항목 ID"),
                fieldWithPath("sections[].questions[].optionGroup.options[].content").description("선택 항목 내용"),
                fieldWithPath("sections[].questions[].optionGroup.options[].isChecked").description("선택 여부"),
                fieldWithPath("sections[].questions[].answer").description("서술형 답변").optional(),
        };

        RestDocumentationResultHandler handler = document(
                "review-detail",
                requestHeaders(requestHeaderDescriptors),
                pathParameters(requestPathDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .pathParam("id", "1")
                .header("groupAccessCode", "00001234")
                .when().get("/v2/reviews/{id}")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 접근_코드가_올바르지_않은_경우_예외를_발생한다() {
        BDDMockito.given(reviewDetailLookupService.getReviewDetail(anyString(), anyLong()))
                .willThrow(new ReviewGroupNotFoundByRequestReviewCodeException(eq(anyString())));

        HeaderDescriptor[] requestHeaderDescriptors = {
                headerWithName("groupAccessCode").description("그룹 접근 코드")
        };

        ParameterDescriptor[] requestPathDescriptors = {
                parameterWithName("id").description("리뷰 ID")
        };

        RestDocumentationResultHandler handler = document(
                "review-detail-invalid-group-access-code",
                requestHeaders(requestHeaderDescriptors),
                pathParameters(requestPathDescriptors)
        );

        givenWithSpec().log().all()
                .pathParam("id", "1")
                .header("groupAccessCode", "00001234")
                .when().get("/v2/reviews/{id}")
                .then().log().all()
                .apply(handler)
                .statusCode(404);
    }
}