package reviewme.api;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import reviewme.member.service.dto.ProfileResponse;

public class AuthApiTest extends ApiTest {

    @Test
    void 깃허브로_인증한다() {
        given(memberService.getProfile(any()))
                .willReturn(new ProfileResponse(1L, "nickname", "profileImageUrl"));

        String request = """
                {
                    "code": "1234567890"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("code").description("깃허브 임시 인증 코드")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("memberId").description("회원 ID"),
                fieldWithPath("nickname").description("깃허브 닉네임"),
                fieldWithPath("profileImageUrl").description("깃허브 프로필 URL")
        };

        RestDocumentationResultHandler handler = document(
                "github-auth",
                requestFields(requestFieldDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/auth/github")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }

    @Test
    void 로그아웃한다() {
        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        RestDocumentationResultHandler handler = document(
                "logout",
                requestCookies(cookieDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "SESSION12345678")
                .when().post("/v2/auth/logout")
                .then().log().all()
                .apply(handler)
                .statusCode(204)
                .header("Set-Cookie", containsString("JSESSIONID=; Path=/; Max-Age=0"));
    }

    @Test
    void 리뷰_그룹_액세스_코드로_비회원을_인증한다() {
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

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        RestDocumentationResultHandler handler = document(
                "review-group-check-access",
                requestFields(requestFieldDescriptors),
                responseCookies(cookieDescriptors)
        );

        givenWithSpec().log().all()
                .body(request)
                .when().post("/v2/auth/group")
                .then().log().all()
                .apply(handler)
                .cookie("JSESSIONID")
                .statusCode(204);
    }
}
