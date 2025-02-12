package reviewme.api;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import reviewme.auth.domain.GitHubMember;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageElementResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupSummaryResponse;

class ReviewGroupApiTest extends ApiTest {

    @Test
    void 비회원용_리뷰_그룹을_생성한다() {
        BDDMockito.given(reviewGroupService.createReviewGroup(any(ReviewGroupCreationRequest.class), nullable(Long.class)))
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
                "guest-review-group-create",
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
    void 회원용_리뷰_그룹을_생성한다() {
        BDDMockito.given(reviewGroupService.createReviewGroup(any(ReviewGroupCreationRequest.class), nullable(Long.class)))
                .willReturn(new ReviewGroupCreationResponse("ABCD1234"));

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        String request = """
                {
                    "revieweeName": "아루",
                    "projectName": "리뷰미"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("reviewRequestCode").description("리뷰 요청 코드")
        };

        RestDocumentationResultHandler handler = document(
                "member-review-group-create",
                requestCookies(cookieDescriptors),
                requestFields(requestFieldDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "ASVNE1VAKDNV4")
                .body(request)
                .when().post("/v2/groups")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_요청_코드로_회원이_만든_리뷰_그룹_정보를_반환한다() {
        BDDMockito.given(reviewGroupLookupService.getReviewGroupSummary(anyString()))
                .willReturn(new ReviewGroupSummaryResponse(1L,"아루", "리뷰미"));

        ParameterDescriptor[] parameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("revieweeId").description("리뷰이 ID"),
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름")
        };

        RestDocumentationResultHandler handler = document(
                "member-review-group-summary",
                queryParameters(parameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/groups/summary")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 리뷰_요청_코드로_비회원이_만든_리뷰_그룹_정보를_반환한다() {
        BDDMockito.given(reviewGroupLookupService.getReviewGroupSummary(anyString()))
                .willReturn(new ReviewGroupSummaryResponse(null, "아루", "리뷰미"));

        ParameterDescriptor[] parameterDescriptors = {
                parameterWithName("reviewRequestCode").description("리뷰 요청 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("revieweeId").description("리뷰이 ID"),
                fieldWithPath("revieweeName").description("리뷰이 이름"),
                fieldWithPath("projectName").description("프로젝트 이름")
        };

        RestDocumentationResultHandler handler = document(
                "guest-review-group-summary",
                queryParameters(parameterDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .queryParam("reviewRequestCode", "ABCD1234")
                .when().get("/v2/groups/summary")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 회원이_생성한_프로젝트_목록을_반환한다() {
        BDDMockito.given(sessionManager.getGitHubMember(any()))
                .willReturn(new GitHubMember(1L, "githubName", "githubURL"));

        ReviewGroupPageResponse response = new ReviewGroupPageResponse(2L, true,
                List.of(
                        new ReviewGroupPageElementResponse("이동훈", "우테코", LocalDate.of(2024, 1, 30), "WOOTECO1", 1),
                        new ReviewGroupPageElementResponse("아루", "리뷰미", LocalDate.of(2024, 1, 5), "ABCD1234", 2)
                )
        );
        BDDMockito.given(reviewGroupLookupService.getMyReviewGroups())
                .willReturn(response);

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("lastReviewGroupId").description("해당 페이지의 마지막 리뷰 그룹 ID"),
                fieldWithPath("isLastPage").description("마지막 페이지 여부"),
                fieldWithPath("reviewGroups[]").description("리뷰 그룹 목록 (생성일 기준 내림차순 정렬)"),
                fieldWithPath("reviewGroups[].revieweeName").description("리뷰이 이름"),
                fieldWithPath("reviewGroups[].projectName").description("프로젝트 이름"),
                fieldWithPath("reviewGroups[].createdAt").description("생성일"),
                fieldWithPath("reviewGroups[].reviewRequestCode").description("리뷰 요청 코드"),
                fieldWithPath("reviewGroups[].reviewCount").description("작성된 리뷰 수")
        };

        RestDocumentationResultHandler handler = document(
                "review-group-list",
                responseFields(responseFieldDescriptors),
                requestCookies(cookieDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "ABCDEFGHI1234")
                .when().get("/v2/groups")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }
}
