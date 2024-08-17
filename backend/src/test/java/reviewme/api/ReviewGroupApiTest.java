package reviewme.api;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;

class ReviewGroupApiTest extends ApiTest {

    @Test
    void 리뷰_그룹을_생성한다() {
        BDDMockito.given(reviewGroupService.createReviewGroup(any(ReviewGroupCreationRequest.class)))
                .willReturn(new ReviewGroupCreationResponse("ABCD1234", "00001234"));

        String request = """
                {
                    "revieweeName": "아루",
                    "projectName": "리뷰미"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("revieweeName").description("리뷰어 이름"),
                fieldWithPath("projectName").description("프로젝트 이름")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("groupAccessCode").description("그룹 접근 코드")
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

}
