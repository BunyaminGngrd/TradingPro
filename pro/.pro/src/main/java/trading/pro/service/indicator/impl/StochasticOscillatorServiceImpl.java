package trading.pro.service.indicator.impl;

import org.springframework.stereotype.Service;
import trading.pro.service.indicator.IStochasticOscillatorService;

import java.util.List;

@Service
public class StochasticOscillatorServiceImpl implements IStochasticOscillatorService {
    @Override
    public double calculateStochasticOscillator(List<Double> closingPrices, int period) {
        double highestHigh = calculateHighestHigh(closingPrices, period);
        double lowestLow = calculateLowestLow(closingPrices, period);

        double currentClose = closingPrices.get(closingPrices.size() - 1);

        return (currentClose - lowestLow) / (highestHigh - lowestLow) * 100;
    }

    private double calculateHighestHigh(List<Double> prices, int period) {
        return prices
                .subList(prices.size() - period, prices.size()).stream().mapToDouble(Double::doubleValue).max().orElse(Double.NaN);
    }

    private double calculateLowestLow(List<Double> prices, int period) {
        return prices
                .subList(prices.size() - period, prices.size()).stream().mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
    }
}
