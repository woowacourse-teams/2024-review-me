package reviewme.api;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;

public class AuthApiTest extends ApiTest {

    @Test
    void 깃허브로_인증한다() {
        String request = """
                {
                     "code": "github_auth_code"
                }
                """;

        FieldDescriptor[] requestFieldDescriptors = {
                fieldWithPath("code").description("깃허브 임시 인증 코드"),
        };

        RestDocumentationResultHandler handler = document(
                "github-auth",
                requestFields(requestFieldDescriptors)
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
                .statusCode(204);
    }
}
