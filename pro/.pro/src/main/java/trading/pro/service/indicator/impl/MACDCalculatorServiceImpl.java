package trading.pro.service.indicator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.IndicatorRequestDTO;
import trading.pro.dto.MacdCalculateResponse;
import trading.pro.dto.RequestResponseType;
import trading.pro.entity.LiveDataEntity;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.IBaseService;
import trading.pro.service.indicator.IEMACalculatorService;
import trading.pro.service.indicator.IMACDCalculatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MACDCalculatorServiceImpl implements IMACDCalculatorService {
    private final LiveDataRepository liveDataRepository;
    private final IEMACalculatorService emaCalculatorService;
    private final IBaseService baseService;

    @Autowired
    public MACDCalculatorServiceImpl(LiveDataRepository liveDataRepository,
                                     IEMACalculatorService emaCalculatorService, IBaseService baseService) {
        this.liveDataRepository = liveDataRepository;
        this.emaCalculatorService = emaCalculatorService;
        this.baseService = baseService;
    }

    @Override
    @LogPerformance
    public RequestResponseType macdSignal(IndicatorRequestDTO indicatorRequestDTO) {
        MacdCalculateResponse response = calculateMACD(indicatorRequestDTO.getStockCode(), indicatorRequestDTO.getPeriod(), indicatorRequestDTO.getStartDate());

        float signalLine = response.getSignalLine().get(response.getSignalLine().size() - 1);
        float macdLine = response.getMacdLine().get(response.getMacdLine().size() - 1);

        if (signalLine > macdLine) {
            return baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(), ResponseMessage.BUY.name());
        }
        return baseService.createResponseMessage(ResponseCode.SUCCESS.getValue(), ResponseMessage.SELL.name());
    }

    @Override
    public MacdCalculateResponse calculateMACD(String stockCode, int period, String startDate) {
        List<LiveDataEntity> stockData = liveDataRepository.findByCodeAndDateAfter(stockCode, startDate);

        if (stockData.size() < period) {
            return null;
        }

        List<Float> closingPrices = new ArrayList<>(stockData.size());
        for (LiveDataEntity entity : stockData) {
            closingPrices.add(entity.getLastPrice());
        }

        return macdCalculator(closingPrices);
    }

    public MacdCalculateResponse macdCalculator(List<Float> closingPrices) {
        MacdCalculateResponse response = new MacdCalculateResponse();
        int shortTermPeriod = 12;
        int longTermPeriod = 26;
        int signalPeriod = 9;

        // Kapanış fiyatlarından kısa ve uzun vadeli EMA'ları hesapla
        List<Float> shortTermEMA = this.emaCalculatorService.calculateEMA(closingPrices, shortTermPeriod);
        List<Float> longTermEMA = this.emaCalculatorService.calculateEMA(closingPrices, longTermPeriod);

        // MACD hattı hesapla
        List<Float> macdLine = new ArrayList<>();
        for (int i = 0; i < shortTermEMA.size(); i++) {
            macdLine.add(shortTermEMA.get(i) - longTermEMA.get(i));
        }

        // Sinyal hattı hesapla
        List<Float> signalLine = this.emaCalculatorService.calculateEMA(macdLine.subList(longTermPeriod - 1, macdLine.size()), signalPeriod);

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
