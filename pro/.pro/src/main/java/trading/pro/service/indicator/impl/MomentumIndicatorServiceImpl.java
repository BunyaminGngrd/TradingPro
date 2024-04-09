package trading.pro.service.indicator.impl;

import org.springframework.stereotype.Service;
import trading.pro.service.indicator.IMomentumIndicatorService;

import java.util.List;

@Service
public class MomentumIndicatorServiceImpl implements IMomentumIndicatorService {
    @Override
    public double calculateMomentum(List<Double> closingPrices, int period) {
        // Geçerli kapanış fiyatı ve belirli bir periyottaki önceki kapanış fiyatını alın
        double currentPrice = closingPrices.get(closingPrices.size() - 1);
        double previousPrice = closingPrices.get(closingPrices.size() - 1 - period);

        // Mevcut fiyat - Belirli periyottaki önceki fiyat
        return currentPrice - previousPrice;
    }
}
