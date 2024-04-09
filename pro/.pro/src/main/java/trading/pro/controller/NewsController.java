package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.CurrencyToAllRequest;
import trading.pro.dto.NewsResponseDTO;
import trading.pro.service.news.INewsService;

@RestController
@RequestMapping("/economy")
public class NewsController {

    private final INewsService newsService;

    @Autowired
    public NewsController(INewsService newsService) {
        this.newsService = newsService;
    }

    @LogPerformance
    @GetMapping("/tr/getNews")
    public ResponseEntity<NewsResponseDTO> getNewsTR() {
        try {
            NewsResponseDTO responseDTO = newsService.getNews("tr");
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @LogPerformance
    @GetMapping("/en/getNews")
    public ResponseEntity<NewsResponseDTO> getNewsEN() {
        try {
            NewsResponseDTO responseDTO = newsService.getNews("en");
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
