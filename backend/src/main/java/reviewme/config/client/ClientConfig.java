package reviewme.config.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@EnableConfigurationProperties(GitHubOAuthProperties.class)
@Configuration
public class ClientConfig {

    @Bean
    public GitHubOAuthClient githubOAuthClient(GitHubOAuthProperties properties) {
        RestClient restClient = RestClient.builder()
                .requestFactory(getClientHttpRequestFactory())
                .build();
        return new GitHubOAuthClient(restClient, properties.clientId(), properties.clientSecret(),
                properties.accessTokenUri(), properties.userInfoUri());
    }

    private JdkClientHttpRequestFactory getClientHttpRequestFactory() {
        JdkClientHttpRequestFactory clientHttpRequestFactory = new JdkClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(3000);
        return clientHttpRequestFactory;
    }
}
