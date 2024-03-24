package trading.pro.service.strategy;

import trading.pro.dto.RequestResponseType;

public interface IBaseStrategyService {
    RequestResponseType calculationOfEmaMacdAndRsiCombination(String stockCode, int period, String startDate);
}
