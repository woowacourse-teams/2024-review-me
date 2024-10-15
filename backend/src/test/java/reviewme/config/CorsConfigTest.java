package reviewme.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import reviewme.reviewgroup.service.ReviewGroupService;

@WebMvcTest(controllers = CorsConfigTest.TestController.class)
abstract class CorsConfigTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ReviewGroupService reviewGroupService;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Controller
    static class TestController {
        @RequestMapping("/test")
        public void test() {
        }
    }
}
