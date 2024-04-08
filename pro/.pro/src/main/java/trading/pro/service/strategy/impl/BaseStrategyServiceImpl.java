package trading.pro.service.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.MacdCalculateResponse;
import trading.pro.dto.RequestResponseType;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.IBaseService;
import trading.pro.service.indicator.IEMACalculatorService;
import trading.pro.service.indicator.IMACDCalculatorService;
import trading.pro.service.indicator.IRsiCalculatorService;
import trading.pro.service.strategy.IBaseStrategyService;

import java.util.List;

@Service
public class BaseStrategyServiceImpl implements IBaseStrategyService {

    private final LiveDataRepository liveDataRepository;
    private final IEMACalculatorService emaCalculatorService;
    private final IMACDCalculatorService macdCalculatorService;
    private final IRsiCalculatorService rsiCalculatorService;
    private final IBaseService baseService;

    @Autowired
    public BaseStrategyServiceImpl(LiveDataRepository liveDataRepository,
                                   IEMACalculatorService emaCalculatorService,
                                   IMACDCalculatorService macdCalculatorService,
                                   IRsiCalculatorService rsiCalculatorService,
                                   IBaseService baseService) {
        this.liveDataRepository = liveDataRepository;
        this.emaCalculatorService = emaCalculatorService;
        this.macdCalculatorService = macdCalculatorService;
        this.rsiCalculatorService = rsiCalculatorService;
        this.baseService = baseService;
    }

    @Override
    public RequestResponseType calculationOfEmaMacdAndRsiCombination(String stockCode, int period, String startDate) {
        try {
            // Calculate RSI
            Double rsi = this.rsiCalculatorService.calculateRsiForStock(stockCode, period, startDate);

            // Calculate MACD
            MacdCalculateResponse macdResponse = this.macdCalculatorService.calculateMACD(stockCode, period, startDate);

            // Calculate EMA
            List<Float> closingPrices = this.baseService.extractClosingPrices(this.liveDataRepository.findByCodeAndDateAfter(stockCode, startDate));
            List<Float> emaValues = this.emaCalculatorService.calculateEMA(closingPrices, period);

            if (rsi != null && macdResponse != null && emaValues != null) {
                float signalLine = macdResponse.getSignalLine().get(macdResponse.getSignalLine().size() - 1);
                float macdLine = macdResponse.getMacdLine().get(macdResponse.getMacdLine().size() - 1);

                // Make buy or sell decision based on RSI, MACD, and EMA values
                if (rsi < 30 && signalLine > macdLine && closingPrices.get(closingPrices.size() - 1) > emaValues.get(emaValues.size() - 1)) {
                    // Buy signal
                    return this.baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(),ResponseMessage.BUY.name(),Response.TRUE.name());
                } else if (rsi > 70 && signalLine < macdLine && closingPrices.get(closingPrices.size() - 1) < emaValues.get(emaValues.size() - 1)) {
                    // Sell signal
                    return this.baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(),ResponseMessage.SELL.name(),Response.FALSE.name());
                } else {
                    // No signal
                    return this.baseService.createResponseMessage(ResponseCode.ERROR.getValue(),ResponseMessage.NO_SIGNAL.name());
                }
            } else {
                return this.baseService.createResponseMessage(ResponseCode.ERROR.getValue(),"There is not enough data to perform calculations: " + stockCode);
            }
        } catch (Exception e) {
            return this.baseService.createResponseMessage(ResponseCode.ERROR.getValue(),"An error occurred: " + e.getMessage());
        }
    }
}
