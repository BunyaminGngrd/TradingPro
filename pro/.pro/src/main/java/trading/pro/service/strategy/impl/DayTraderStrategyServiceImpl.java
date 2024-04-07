package trading.pro.service.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes.*;
import trading.pro.common.LogPerformance;
import trading.pro.dto.DayTraderStrategyResponse;
import trading.pro.dto.RequestResponseType;
import trading.pro.dto.StrategyResponse;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.strategy.IBaseStrategyService;
import trading.pro.service.strategy.IDayTraderStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DayTraderStrategyServiceImpl implements IDayTraderStrategy {

    private final LiveDataRepository liveDataRepository;
    private final IBaseStrategyService baseStrategyService;

    @Autowired
    public DayTraderStrategyServiceImpl(LiveDataRepository liveDataRepository,
                                        IBaseStrategyService baseStrategyService) {
        this.liveDataRepository = liveDataRepository;
        this.baseStrategyService = baseStrategyService;
    }

    @LogPerformance
    @Override
    public DayTraderStrategyResponse calculateStrategy() {
        DayTraderStrategyResponse response = new DayTraderStrategyResponse();
        response.setResponseList(getStrategyResponses());
        return response;
    }

    public List<StrategyResponse> getStrategyResponses() {
        List<StrategyResponse> strategyResponseList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int shortTermPeriod = 3;
        int middleTermPeriod = 5;
        int longTermPeriod = 9;

        List<String> distinctStockCodes = liveDataRepository.findAllDistinctCodes();

        // ExecutorService oluştur
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<StrategyResponse>> futures = new ArrayList<>();
        distinctStockCodes.forEach(stockCode -> {
            Callable<StrategyResponse> calculationTask = () -> {
                RequestResponseType shortTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, shortTermPeriod, today.minusDays(shortTermPeriod).toString());
                RequestResponseType middleTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, middleTermPeriod, today.minusDays(middleTermPeriod).toString());
                RequestResponseType longTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, longTermPeriod, today.minusDays(longTermPeriod).toString());

                StrategyResponse strategyResponse = new StrategyResponse();
                if (shortTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue()) && middleTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue()) && longTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue())) {
                    strategyResponse.setStrategyResultMessage(shortTermResponse.getResponseMessage());
                    strategyResponse.setBuySignal(true);
                } else {
                    strategyResponse.setStrategyResultMessage(ResponseMessage.NO_SIGNAL.name());
                    strategyResponse.setBuySignal(false);
                }

                strategyResponse.setStockCode(stockCode);
                return strategyResponse;
            };
            futures.add(executor.submit(calculationTask));
        });

        for (Future<StrategyResponse> future : futures) {
            try {
                StrategyResponse strategyResponse = future.get();
                strategyResponseList.add(strategyResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return strategyResponseList;
    }
}
