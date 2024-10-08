package reviewme.api;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.cookies.CookieDescriptor;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;

class HighlightApiTest extends ApiTest {

    @Test
    void 존재하는_답변에_하이라이트를_생성한다() {
        String request = """
                 {
                     "highlights": [{
                       "answerId": 3,
                       "lines": [{
                           "index": 5,
                           "ranges": [{
                               "startIndex": 6,
                               "endIndex": 9
                           }]
                        }]
                     }]
                 }
                """;

        CookieDescriptor[] cookieDescriptors = {
                cookieWithName("JSESSIONID").description("세션 ID")
        };

        FieldDescriptor[] requestFields = {
                fieldWithPath("highlights").description("하이라이트 목록"),
                fieldWithPath("highlights[].answerId").description("답변 ID"),
                fieldWithPath("highlights[].lines[].index").description("개행으로 구분되는 라인 번호, 0-based"),
                fieldWithPath("highlights[].lines[].ranges[].startIndex").description("하이라이트 시작 인덱스, 0-based"),
                fieldWithPath("highlights[].lines[].ranges[].endIndex").description("하이라이트 끝 인덱스, 0-based")
        };

        RestDocumentationResultHandler handler = document(
                "highlight-answer",
                requestFields(requestFields),
                requestCookies(cookieDescriptors)
        );

        givenWithSpec().log().all()
                .cookie("JSESSIONID", "AVEBNKLCL13TNVZ")
                .body(request)
                .when().post("/v2/highlight")
                .then().log().all()
                .apply(handler)
                .status(HttpStatus.OK);
    }
}
