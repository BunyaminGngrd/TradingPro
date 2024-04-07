package trading.pro.service.indicator.impl;

import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.service.indicator.IEMACalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class EMACalculatorServiceImpl implements IEMACalculatorService {
    @LogPerformance
    @Override
    public List<Float> calculateEMA(List<Float> values, int period) {
        List<Float> emaValues = new ArrayList<>();
        float multiplier = 2.0f / (period + 1);

        // İlk EMA değeri, ilk veri değeri ile aynıdır
        emaValues.add(values.get(0));

        // EMA hesapla
        for (int i = 1; i < values.size(); i++) {
            float ema = (values.get(i) - emaValues.get(i - 1)) * multiplier + emaValues.get(i - 1);
            emaValues.add(ema);
        }

        return emaValues;
    }
}
