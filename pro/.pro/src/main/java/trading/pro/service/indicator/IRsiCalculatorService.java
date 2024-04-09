package trading.pro.service.indicator;

import trading.pro.dto.IndicatorRequestDTO;
import trading.pro.dto.RequestResponseType;

public interface IRsiCalculatorService {
    RequestResponseType rsiSignal(IndicatorRequestDTO indicatorRequestDTO);
    Double calculateRsiForStock(String stockCode, int period, String startDate);
}
