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

    @LogPerformance
    @Override
    public Double calculateRsiForStock(String stockCode, int period, String startDate) {
        List<LiveDataEntity> stockData = liveDataRepository.findByCodeAndDateAfter(stockCode, startDate);

        if (stockData.size() < period) {
            // Hesaplama yapmak için yeterli veri yok.
            return null;
        }

        List<Double> priceChanges = new ArrayList<>();

        for (int i = 1; i < stockData.size(); i++) {
            Double priceChange = (double) (stockData.get(i).getLastPrice() - stockData.get(i - 1).getLastPrice());
            priceChanges.add(priceChange);
        }

        List<Double> positiveChanges = priceChanges.stream().filter(change -> change > 0).toList();
        List<Double> negativeChanges = priceChanges.stream().filter(change -> change < 0).map(Math::abs).toList();

        double averageGain = positiveChanges.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double averageLoss = negativeChanges.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double rs = (averageGain == 0) ? 0 : (averageGain / averageLoss);
        return 100 - (100 / (1 + rs));
    }
}

