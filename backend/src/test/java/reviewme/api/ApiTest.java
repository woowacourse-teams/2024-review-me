package reviewme.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
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
import reviewme.review.service.CreateReviewService;
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewService;
import reviewme.reviewgroup.controller.ReviewGroupController;
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
    protected ReviewService reviewService;

    @MockBean
    protected ReviewGroupService reviewGroupService;

    @MockBean
    protected TemplateService templateService;

    @MockBean
    protected CreateReviewService createReviewService;

    @MockBean
    protected ReviewDetailLookupService reviewDetailLookupService;

    @BeforeEach
    void setUpRestDocs(WebApplicationContext context, RestDocumentationContextProvider provider) {
        UriModifyingOperationPreprocessor uriModifier = modifyUris()
                .scheme("https")
                .host("api.review-me.page")
                .removePort();
        HeadersModifyingOperationPreprocessor requestHeaderModifier = modifyHeaders()
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
                .build();

        spec = RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .mockMvc(mockMvc);
    }

    protected MockMvcRequestSpecification givenWithSpec() {
        return spec;
    }
}
