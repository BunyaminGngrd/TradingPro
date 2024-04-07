package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.*;
import trading.pro.service.ICurrencyToAllService;

@RestController
@RequestMapping("/currency")
public class CurrencyToAllController {
    private final ICurrencyToAllService currencyToAllService;

    @Autowired
    public CurrencyToAllController(ICurrencyToAllService currencyToAllService) {
        this.currencyToAllService = currencyToAllService;
    }

    @LogPerformance
    @PostMapping("/all")
    public ResponseEntity<CurrencyToAllResponseDTO> currencyToAll(@RequestBody CurrencyToAllRequest currencyToAllRequest) {
        try {
            CurrencyToAllResponseDTO responseDTO = currencyToAllService.currencyToAll(currencyToAllRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @LogPerformance
    @PostMapping("/desired")
    public ResponseEntity<ConvertToDesiredCurrencyResponse> currencyToDesired(@RequestBody ConvertToDesiredCurrencyRequest convertToDesiredCurrencyRequest) {
        try {
            ConvertToDesiredCurrencyResponse responseDTO = currencyToAllService.convertToDesiredCurrency(convertToDesiredCurrencyRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @LogPerformance
    @GetMapping("/allCurrency")
    public ResponseEntity<AllCurrencyResponse> currencyToDesired() {
        try {
            AllCurrencyResponse responseDTO = currencyToAllService.allCurrency();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
