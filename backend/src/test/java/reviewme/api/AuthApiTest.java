package reviewme.api;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

public class AuthApiTest extends ApiTest {

    @Test
    void 깃허브로_인증한다() {
        ParameterDescriptor[] parameterDescriptors = {
                parameterWithName("code").description("깃허브 임시 인증 코드")
        };

        RestDocumentationResultHandler handler = document(
                "github-auth",
                queryParameters(parameterDescriptors)
        );

        givenWithSpec().log().all()
                .when().get("/v2/auth/github?code=github_auth_code")
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
                .statusCode(204);
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
                .when().post("/v2/auth/review-group")
                .then().log().all()
                .apply(handler)
                .cookie("JSESSIONID")
                .statusCode(204);
    }
}
