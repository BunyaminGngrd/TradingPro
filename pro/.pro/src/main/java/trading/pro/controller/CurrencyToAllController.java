package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/all")
    public ResponseEntity<CurrencyToAllResponseDTO> currencyToAll(CurrencyToAllRequest currencyToAllRequest) {
        try {
            CurrencyToAllResponseDTO responseDTO = currencyToAllService.currencyToAll(currencyToAllRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/desired")
    public ResponseEntity<ConvertToDesiredCurrencyResponse> currencyToDesired(ConvertToDesiredCurrencyRequest convertToDesiredCurrencyRequest) {
        try {
            ConvertToDesiredCurrencyResponse responseDTO = currencyToAllService.convertToDesiredCurrency(convertToDesiredCurrencyRequest);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
