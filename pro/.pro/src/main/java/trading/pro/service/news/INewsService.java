package trading.pro.service.news;

import trading.pro.dto.NewsResponseDTO;

public interface INewsService {
    NewsResponseDTO getNews(String country);
}
