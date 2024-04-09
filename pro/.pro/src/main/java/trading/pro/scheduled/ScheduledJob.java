package trading.pro.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledJob {

    @Autowired
    private RestTemplate restTemplate;
    private final String strategySaveApiUrl;
    private final String strategyDeleteApiUrl;
    private final String liveDataApiUrl;

    @Autowired
    public ScheduledJob(String strategySaveApiUrl,
                        String strategyDeleteApiUrl,
                        String liveDataApiUrl) {
        this.strategySaveApiUrl = strategySaveApiUrl;
        this.strategyDeleteApiUrl = strategyDeleteApiUrl;
        this.liveDataApiUrl = liveDataApiUrl;
    }

    @Scheduled(cron = "0 0 7 * * ?") // Her gün saat 07:00'de çalışır
    public void triggerStrategyApi() {
        try {
            restTemplate.getForObject(liveDataApiUrl, Void.class);
            restTemplate.getForObject(strategyDeleteApiUrl, Void.class);
            restTemplate.getForObject(strategySaveApiUrl, Void.class);
            System.out.println("Scheduled job başarılı.");
        } catch (Exception e) {
            System.err.println("Scheduled job tetiklenirken bir hata oluştu: " + e.getMessage());
        }
    }
}

