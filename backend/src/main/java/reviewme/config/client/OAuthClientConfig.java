package reviewme.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class OAuthClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory())
                .build();
    }

    private JdkClientHttpRequestFactory getClientHttpRequestFactory() {
        JdkClientHttpRequestFactory clientHttpRequestFactory = new JdkClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(3000);
        return clientHttpRequestFactory;
    }

    @Bean
    public GitHubOAuthClient githubOAuthClient(GitHubOAuthProperties properties, RestClient restClient) {
        return new GitHubOAuthClient(restClient, properties);
    }
}
