package trading.pro.service.indicator;

import trading.pro.dto.IndicatorRequestDTO;
import trading.pro.dto.MacdCalculateResponse;
import trading.pro.dto.RequestResponseType;

public interface IMACDCalculatorService {
    RequestResponseType macdSignal(IndicatorRequestDTO indicatorRequestDTO);
    MacdCalculateResponse calculateMACD(String stockCode, int period, String startDate);
}
