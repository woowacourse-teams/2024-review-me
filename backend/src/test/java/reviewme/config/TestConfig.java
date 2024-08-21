package reviewme.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reviewme.support.DatabaseCleaner;

@TestConfiguration
public class TestConfig {

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }
}
