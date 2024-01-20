package trading.pro.service.indicator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.dto.MacdCalculateResponse;
import trading.pro.entity.LiveDataEntity;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.indicator.IEMACalculatorService;
import trading.pro.service.indicator.IMACDCalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MACDCalculatorServiceImpl implements IMACDCalculatorService {
    private final LiveDataRepository liveDataRepository;
    private final IEMACalculatorService emaCalculatorService;

    @Autowired
    public MACDCalculatorServiceImpl(LiveDataRepository liveDataRepository,
                                     IEMACalculatorService emaCalculatorService) {
        this.liveDataRepository = liveDataRepository;
        this.emaCalculatorService = emaCalculatorService;
    }

    @Override
    public MacdCalculateResponse calculateMACD(String stockCode, int period, String startDate) {
        // Veritabanından verileri çek
        List<LiveDataEntity> stockData = liveDataRepository.findByCodeAndDateAfter(stockCode, startDate);

        if (stockData.size() < period) {
            // Hesaplama yapmak için yeterli veri yok.
            return null;
        }

        // Fiyatları içeren liste
        List<Float> closingPrices = extractClosingPrices(stockData);

        // MACD hesapla
        return macdCalculator(closingPrices);
    }

    private List<Float> extractClosingPrices(List<LiveDataEntity> stockData) {
        List<Float> closingPrices = new ArrayList<>();
        for (LiveDataEntity data : stockData) {
            closingPrices.add(data.getLastPrice());
        }
        return closingPrices;
    }

    public MacdCalculateResponse macdCalculator(List<Float> closingPrices) {
        MacdCalculateResponse response = new MacdCalculateResponse();
        int shortTermPeriod = 12;
        int longTermPeriod = 26;
        int signalPeriod = 9;

        List<Float> shortTermEMA = this.emaCalculatorService.calculateEMA(closingPrices, shortTermPeriod);
        List<Float> longTermEMA = this.emaCalculatorService.calculateEMA(closingPrices, longTermPeriod);

        List<Float> macdLine = new ArrayList<>();
        List<Float> signalLine = new ArrayList<>();

        // MACD hattı hesapla
        for (int i = 0; i < closingPrices.size(); i++) {
            macdLine.add(shortTermEMA.get(i) - longTermEMA.get(i));
        }

        // Sinyal hattı hesapla
        signalLine = this.emaCalculatorService.calculateEMA(macdLine.subList(longTermPeriod - 1, macdLine.size()), signalPeriod);

        // Histogram hesapla
        List<Float> histogram = new ArrayList<>();
        for (int i = 0; i < signalLine.size(); i++) {
            histogram.add(macdLine.get(i + longTermPeriod - 1) - signalLine.get(i));
        }

        response.setMacdLine(macdLine);
        response.setSignalLine(signalLine);
        response.setHistogram(histogram);

        return response;
    }
}
