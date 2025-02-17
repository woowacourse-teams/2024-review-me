package reviewme.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import reviewme.member.service.dto.ProfileResponse;

public class MemberApiTest extends ApiTest {

    @Test
    void 내_프로필을_불러온다() {
        given(memberService.getProfile(any()))
                .willReturn(new ProfileResponse(1L, "nickname", "profileImageUrl"));

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        FieldDescriptor[] responseFieldDescriptors = {
                fieldWithPath("memberId").description("회원 ID"),
                fieldWithPath("nickname").description("닉네임"),
                fieldWithPath("profileImageUrl").description("프로필 이미지 URL")
        };

        RestDocumentationResultHandler handler = document(
                "my-profile",
                requestCookies(cookieDescriptors),
                responseFields(responseFieldDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "SESSION12345678")
                .when().get("/v2/members/profile")
                .then().log().all()
                .apply(handler)
                .statusCode(200);
    }
}
