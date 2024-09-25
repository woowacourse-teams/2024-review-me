package reviewme.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Profile("prod")
@Configuration
public class ReplicationDatasourceConfig {

    public static final String WRITE_DATA_SOURCE_NAME = "writeDataSource";
    public static final String READ_DATA_SOURCE_NAME = "readDataSource";
    public static final String ROUTING_DATA_SOURCE_NAME = "routingDataSource";

    @Bean(name = WRITE_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.write.hikari")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READ_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.read.hikari")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    DataSource routingDataSource(
            @Qualifier(WRITE_DATA_SOURCE_NAME) DataSource writeDataSource,
            @Qualifier(READ_DATA_SOURCE_NAME) DataSource readDataSource) {
        AbstractRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITE, writeDataSource);
        dataSourceMap.put(DataSourceType.READ, readDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE_NAME) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}

