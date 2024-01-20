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
}
