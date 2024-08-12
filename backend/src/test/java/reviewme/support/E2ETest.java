package reviewme.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import reviewme.config.TestConfig;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@ExtendWith(DatabaseCleanerExtension.class)
public abstract class E2ETest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
