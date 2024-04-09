package trading.pro.service.indicator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.IndicatorRequestDTO;
import trading.pro.dto.RequestResponseType;
import trading.pro.entity.LiveDataEntity;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.IBaseService;
import trading.pro.service.indicator.IRsiCalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RsiCalculatorServiceImpl implements IRsiCalculatorService {

    private final LiveDataRepository liveDataRepository;
    private final IBaseService baseService;

    @Autowired
    public RsiCalculatorServiceImpl(LiveDataRepository liveDataRepository,
                                    IBaseService baseService) {
        this.liveDataRepository = liveDataRepository;
        this.baseService = baseService;
    }

    @Override
    @LogPerformance
    public RequestResponseType rsiSignal(IndicatorRequestDTO indicatorRequestDTO) {
        try {
            Double rsi = calculateRsiForStock(indicatorRequestDTO.getStockCode(), indicatorRequestDTO.getPeriod(), indicatorRequestDTO.getStartDate());

            if (rsi < 30) {
                return baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(), ResponseMessage.BUY.name());
            } else if (rsi < 70) {
                return baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(), ResponseMessage.SELL.name());
            } else {
                return baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(), ResponseMessage.NO_SIGNAL.name());
            }
        } catch (Exception e) {
            return baseService.createResponseMessage(ResponseCode.ERROR.getValue(), e.getMessage());
        }
    }

    @Override
    public Double calculateRsiForStock(String stockCode, int period, String startDate) {
        List<LiveDataEntity> stockData = liveDataRepository.findByCodeAndDateAfter(stockCode, startDate);

        if (stockData.size() < period) {
            return null; // Hesaplama yapmak için yeterli veri yok.
        }

        List<Double> priceChanges = new ArrayList<>();
        for (int i = 1; i < stockData.size(); i++) {
            double priceChange = stockData.get(i).getLastPrice() - stockData.get(i - 1).getLastPrice();
            priceChanges.add(priceChange);
        }

        double sumOfGains = priceChanges.stream()
                .filter(change -> change > 0)
                .mapToDouble(Double::doubleValue)
                .sum();

        double sumOfLosses = priceChanges.stream()
                .filter(change -> change < 0)
                .mapToDouble(change -> Math.abs(change))
                .sum();

        double avgGain = sumOfGains / period;
        double avgLoss = sumOfLosses / period;

        double rs = (avgLoss == 0) ? Double.POSITIVE_INFINITY : avgGain / avgLoss;

        return 100 - (100 / (1 + rs));
    }
}

