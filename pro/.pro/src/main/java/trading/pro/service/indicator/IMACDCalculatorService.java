package trading.pro.service.indicator;

import trading.pro.dto.MacdCalculateResponse;

public interface IMACDCalculatorService {
    MacdCalculateResponse calculateMACD(String stockCode, int period, String startDate);
}
