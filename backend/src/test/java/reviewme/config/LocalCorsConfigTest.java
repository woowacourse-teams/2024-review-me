package reviewme.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@ActiveProfiles("local")
class LocalCorsConfigTest extends CorsConfigTest {

    @Test
    void 로컬_프로파일에서는_외부_접근에_대해서도_허용한다() throws Exception {
        String domain = "http://test-domain.com";
        mockMvc.perform(options("/test")
                        .header(HttpHeaders.ORIGIN, domain)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, domain))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true"));
    }
}
