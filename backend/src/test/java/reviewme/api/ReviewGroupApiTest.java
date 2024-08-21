package reviewme.api;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.reviewgroup.service.dto.CheckValidAccessResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;

class ReviewGroupApiTest extends ApiTest {

    @Test
    void 리뷰_그룹을_생성한다() {
        BDDMockito.given(reviewGroupService.createReviewGroup(any(ReviewGroupCreationRequest.class)))
                .willReturn(new ReviewGroupCreationResponse("ABCD1234"));

        String request = """
                {
                    "revieweeName": "아루",
                    "projectName": "리뷰미",
                    "groupAccessCode": "12341234"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름"),
                fieldWithPath("groupAccessCode").description("리뷰 확인 코드(비밀번호)")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드")
        };

        RestDocumentationResultHandler handler = document(
                "review-group-create",
                requestFields(requestFieldDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/groups")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_요청_코드로_리뷰_그룹_정보를_반환한다() {
        BDDMockito.given(reviewGroupLookupService.getReviewGroupSummary(anyString()))
                .willReturn(new ReviewGroupResponse("아루", "리뷰미"));

        ParameterDescriptor[] parameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름")
        };

        RestDocumentationResultHandler handler = document(
                "review-group-summary",
                queryParameters(parameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/groups")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_그룹_코드와_액세스_코드로_일치_여부를_판단한다() {
        BDDMockito.given(reviewGroupService.checkGroupAccessCode(any(CheckValidAccessRequest.class)))
                .willReturn(new CheckValidAccessResponse(true));

        String request = """
                {
                    "reviewRequestCode": "ABCD1234",
                    "groupAccessCode": "00001234"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("groupAccessCode").description("그룹 접근 코드 (비밀번호)")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("hasAccess").description("코드 일치 여부 (비밀번호 일치)")
        };

        RestDocumentationResultHandler handler = document(
                "review-group-check-access",
                requestFields(requestFieldDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/groups/check")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }
}
