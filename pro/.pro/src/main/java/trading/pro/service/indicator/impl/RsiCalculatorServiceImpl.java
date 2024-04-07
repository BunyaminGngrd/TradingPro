package trading.pro.service.indicator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.entity.LiveDataEntity;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.indicator.IRsiCalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RsiCalculatorServiceImpl implements IRsiCalculatorService {

    private final LiveDataRepository liveDataRepository;

    @Autowired
    public RsiCalculatorServiceImpl(LiveDataRepository liveDataRepository) {
        this.liveDataRepository = liveDataRepository;
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

