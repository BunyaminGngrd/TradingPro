package trading.pro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.pro.dto.DayTraderStrategyResponse;
import trading.pro.service.strategy.IDayTraderStrategy;

@RestController
@RequestMapping("/strategy")
public class StrategyController {

    private final IDayTraderStrategy dayTraderStrategy;

    public StrategyController(IDayTraderStrategy dayTraderStrategy) {
        this.dayTraderStrategy = dayTraderStrategy;
    }

    @GetMapping("/trader/day")
    public ResponseEntity<DayTraderStrategyResponse> currencyToAll() {
        try {
            return ResponseEntity.ok(dayTraderStrategy.calculateStrategy());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
