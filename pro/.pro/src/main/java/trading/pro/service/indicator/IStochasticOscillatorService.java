package trading.pro.service.indicator;

import java.util.List;

public interface IStochasticOscillatorService {
    double calculateStochasticOscillator(List<Double> closingPrices, int period);
}
