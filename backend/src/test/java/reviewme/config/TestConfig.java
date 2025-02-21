package reviewme.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import reviewme.support.CacheCleaner;
import reviewme.support.DatabaseCleaner;

@TestConfiguration
public class TestConfig {

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }

    @Bean
    public CacheCleaner cacheCleaner(CacheManager cacheManager) {
        return new CacheCleaner(cacheManager);
    }
}
