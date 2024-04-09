package trading.pro.service.news.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.dto.NewsResponseDTO;
import trading.pro.service.news.INewsService;

@Service
public class NewsServiceImpl implements INewsService {

    private final String apiKey;

    @Autowired
    public NewsServiceImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    @LogPerformance
    public NewsResponseDTO getNews(String country) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpResponse<String> response = Unirest.get("https://api.collectapi.com/news/getNews?country="+country+"&tag=economy")
                    .header("content-type", "application/json")
                    .header("authorization", apiKey)
                    .asString();

            return objectMapper.readValue(response.getBody(), NewsResponseDTO.class);
        } catch (Exception ex) {
            throw new RuntimeException("NewsServiceImpl.getNews().ERROR : " + ex.getMessage());
        }
    }
}
