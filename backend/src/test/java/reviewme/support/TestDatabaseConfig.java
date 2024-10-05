package reviewme.support;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration
public class TestDatabaseConfig {

    private static final String IMAGE_NAME = "mysql:8.0.39";
    private static final JdbcDatabaseContainer<?> container = new MySQLContainer<>(IMAGE_NAME);

    static {
        container.start();
    }

    @Bean
    @Primary
    public static DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .driverClassName(container.getDriverClassName())
                .build();
    }

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }
}
