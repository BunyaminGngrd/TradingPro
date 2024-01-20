package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.pro.dto.LiveDataResponseDTO;
import trading.pro.entity.LiveDataEntity;
import trading.pro.service.ICollectAPIService;

@RestController
@RequestMapping("/api/collect")
public class CollectAPIController {

    private final ICollectAPIService collectAPIService;

    @Autowired
    public CollectAPIController(ICollectAPIService collectAPIService) {
        this.collectAPIService = collectAPIService;
    }

    @GetMapping("/livedata")
    public ResponseEntity<LiveDataResponseDTO> getAllData() {
        try {
           LiveDataResponseDTO responseDTO = collectAPIService.getAllData();
           return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/livedata/{code}")
    public ResponseEntity<LiveDataEntity> findByData(@PathVariable String code) {
        try {
            LiveDataEntity data = collectAPIService.findByCode(code);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
