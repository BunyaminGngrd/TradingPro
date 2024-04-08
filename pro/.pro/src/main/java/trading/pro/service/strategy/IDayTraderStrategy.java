package trading.pro.service.strategy;

import trading.pro.dto.DayTraderStrategyResponse;

public interface IDayTraderStrategy {
    DayTraderStrategyResponse calculateStrategy();

    void saveCalculateStrategy();

    void deleteCalculateStrategy();
}
