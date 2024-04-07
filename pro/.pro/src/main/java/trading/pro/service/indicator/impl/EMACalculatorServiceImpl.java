package trading.pro.service.indicator.impl;

import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.service.indicator.IEMACalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class EMACalculatorServiceImpl implements IEMACalculatorService {
    @Override
    public List<Float> calculateEMA(List<Float> values, int period) {
        List<Float> emaValues = new ArrayList<>();
        float multiplier = 2.0f / (period + 1);

        emaValues.add(values.get(0));

        float diff = values.get(1) - emaValues.get(0);

        for (int i = 1; i < values.size(); i++) {
            float ema = emaValues.get(i - 1) + multiplier * diff;
            emaValues.add(ema);
            diff = values.get(i) - ema;
        }

        return emaValues;
    }
}
