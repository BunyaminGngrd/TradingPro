package trading.pro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${api.key.myApiKey}")
    private String apiKey;

    @Bean
    public String apiKey() {
        return apiKey;
    }

    @Value("${scheduledSave.strategy.api.url}")
    private String strategySaveApiUrl;

    @Bean
    public String strategySaveApiUrl() {
        return strategySaveApiUrl;
    }

    @Value("${scheduledDelete.strategy.api.url}")
    private String strategyDeleteApiUrl;

    @Bean
    public String strategyDeleteApiUrl() {
        return strategyDeleteApiUrl;
    }

    @Value("${livedata.api.url}")
    private String liveDataApiUrl;

    @Bean
    public String liveDataApiUrl() {
        return liveDataApiUrl;
    }
}
