package trading.pro.service.indicator;

import java.util.List;

public interface IMomentumIndicatorService {
    double calculateMomentum(List<Double> closingPrices, int period);
}
