package reviewme.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import jakarta.servlet.Filter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcOperationPreprocessorsConfigurer;
import org.springframework.restdocs.operation.preprocess.HeadersModifyingOperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reviewme.review.controller.ReviewController;
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewListLookupService;
import reviewme.review.service.ReviewRegisterService;
import reviewme.reviewgroup.controller.ReviewGroupController;
import reviewme.reviewgroup.service.ReviewGroupLookupService;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.template.controller.TemplateController;
import reviewme.template.service.TemplateService;

@WebMvcTest({
        ReviewGroupController.class,
        ReviewController.class,
        TemplateController.class
})
@ExtendWith(RestDocumentationExtension.class)
public abstract class ApiTest {

    private MockMvcRequestSpecification spec;

    @MockBean
    protected ReviewListLookupService reviewListLookupService;

    @MockBean
    protected ReviewGroupService reviewGroupService;

    @MockBean
    protected TemplateService templateService;

    @MockBean
    protected ReviewRegisterService reviewRegisterService;

    @MockBean
    protected ReviewDetailLookupService reviewDetailLookupService;

    @MockBean
    protected ReviewGroupLookupService reviewGroupLookupService;

    Filter sessionCookieFilter = (request, response, chain) -> {
        chain.doFilter(request, response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            sessionCookie.setSecure(true);
            ((HttpServletResponse) response).addCookie(sessionCookie);
        }
    };

    @BeforeEach
    void setUpRestDocs(WebApplicationContext context, RestDocumentationContextProvider provider) {
        UriModifyingOperationPreprocessor uriModifier = modifyUris()
                .scheme("https")
                .host("api.review-me.page")
                .removePort();
        HeadersModifyingOperationPreprocessor requestHeaderModifier = modifyHeaders()
                .set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .remove(HttpHeaders.CONTENT_LENGTH);
        HeadersModifyingOperationPreprocessor responseHeaderModifier = modifyHeaders()
                .remove(HttpHeaders.CONTENT_LENGTH)
                .remove(HttpHeaders.CONNECTION)
                .remove(HttpHeaders.TRANSFER_ENCODING)
                .remove(HttpHeaders.VARY)
                .remove("Keep-Alive");

        MockMvcOperationPreprocessorsConfigurer configurer = documentationConfiguration(provider)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint(), uriModifier, requestHeaderModifier)
                .withResponseDefaults(prettyPrint(), responseHeaderModifier);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(configurer)
                .addFilters(sessionCookieFilter)
                .build();

        spec = RestAssuredMockMvc.given()
                .sessionAttr("reviewRequestCode", "12341234")
                .mockMvc(mockMvc);
    }

    protected MockMvcRequestSpecification givenWithSpec() {
        return spec.contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
