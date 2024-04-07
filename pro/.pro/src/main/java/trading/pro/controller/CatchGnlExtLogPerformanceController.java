package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.pro.dto.RequestResponseType;
import trading.pro.service.ICatchGnlExtLogPerformanceService;

@RestController
@RequestMapping("/catch/log/")
public class CatchGnlExtLogPerformanceController {

    private final ICatchGnlExtLogPerformanceService catchGnlExtLogPerformanceService;

    @Autowired
    public CatchGnlExtLogPerformanceController(ICatchGnlExtLogPerformanceService catchGnlExtLogPerformanceService) {
        this.catchGnlExtLogPerformanceService = catchGnlExtLogPerformanceService;
    }

    @GetMapping("/clear")
    public ResponseEntity<RequestResponseType> catchClear() {
        try {
            return ResponseEntity.ok(catchGnlExtLogPerformanceService.catchClear());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
