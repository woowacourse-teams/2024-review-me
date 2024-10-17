package reviewme.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@ActiveProfiles("dev")
class ExternalCorsConfigTest extends CorsConfigTest {

    @Autowired
    private CorsProperties corsProperties;

    @Test
    void 로컬이_아닌_프로파일의_외부_요청은_허락하지_않는다() throws Exception {
        String origin = "http://denied-domain.com";
        mockMvc.perform(options("/test")
                        .header(HttpHeaders.ORIGIN, origin)
                        .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "GET")
                ).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 로컬이_아닌_프로파일의_리뷰미_도메인_요청은_허락한다() throws Exception {
        String origin = corsProperties.allowedOrigins().get(0);
        mockMvc.perform(options("/test")
                        .header(HttpHeaders.ORIGIN, origin)
                        .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "GET")
                ).andDo(print())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin))
                .andExpect(status().isOk());
    }
}
