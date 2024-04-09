package trading.pro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${api.key.myApiKey}")
    private String apiKey;

    @Value("${livedata.api.url}")
    private String liveDataApiUrl;

    @Value("${scheduledDelete.strategy.api.url}")
    private String strategyDeleteApiUrl;

    @Value("${scheduledSave.strategy.api.url}")
    private String strategySaveApiUrl;

    @Bean
    public String apiKey() {
        return apiKey;
    }

    @Bean
    public String strategySaveApiUrl() {
        return strategySaveApiUrl;
    }

    @Bean
    public String strategyDeleteApiUrl() {
        return strategyDeleteApiUrl;
    }

    @Bean
    public String liveDataApiUrl() {
        return liveDataApiUrl;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
