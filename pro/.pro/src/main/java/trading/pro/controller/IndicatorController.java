package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.IndicatorRequestDTO;
import trading.pro.dto.RequestResponseType;
import trading.pro.service.indicator.IMACDCalculatorService;
import trading.pro.service.indicator.IRsiCalculatorService;

@RestController
@RequestMapping("/indicator")
public class IndicatorController {
    private final IMACDCalculatorService macdCalculatorService;
    private final IRsiCalculatorService rsiCalculatorService;

    @Autowired
    public IndicatorController(IMACDCalculatorService macdCalculatorService,
                               IRsiCalculatorService rsiCalculatorService) {
        this.macdCalculatorService = macdCalculatorService;
        this.rsiCalculatorService = rsiCalculatorService;
    }

    @LogPerformance
    @PostMapping ("/macd")
    public ResponseEntity<RequestResponseType> macdSignal(IndicatorRequestDTO indicatorRequestDTO) {
        try {
            return ResponseEntity.ok(macdCalculatorService.macdSignal(indicatorRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @LogPerformance
    @PostMapping ("/rsi")
    public ResponseEntity<RequestResponseType> rsiSignal(IndicatorRequestDTO indicatorRequestDTO) {
        try {
            return ResponseEntity.ok(rsiCalculatorService.rsiSignal(indicatorRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
