package trading.pro.service.indicator;

import java.util.List;

public interface IEMACalculatorService {
    List<Float> calculateEMA(List<Float> values, int period);
}
