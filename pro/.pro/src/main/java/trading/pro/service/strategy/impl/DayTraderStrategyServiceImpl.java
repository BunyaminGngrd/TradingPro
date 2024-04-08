package trading.pro.service.strategy.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.GnlEnumTypes.ResponseCode;
import trading.pro.common.GnlEnumTypes.ResponseMessage;
import trading.pro.common.LogPerformance;
import trading.pro.dto.DayTraderStrategyResponse;
import trading.pro.dto.RequestResponseType;
import trading.pro.dto.StrategyResponse;
import trading.pro.entity.StrategyResponseEntity;
import trading.pro.mapper.StrategyResponseMapper;
import trading.pro.repository.LiveDataRepository;
import trading.pro.repository.StrategyResponseRepository;
import trading.pro.service.strategy.IBaseStrategyService;
import trading.pro.service.strategy.IDayTraderStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DayTraderStrategyServiceImpl implements IDayTraderStrategy {

    private final LiveDataRepository liveDataRepository;
    private final IBaseStrategyService baseStrategyService;
    private final StrategyResponseRepository strategyResponseRepository;
    private final StrategyResponseMapper mapper = Mappers.getMapper(StrategyResponseMapper.class);

    @Autowired
    public DayTraderStrategyServiceImpl(LiveDataRepository liveDataRepository,
                                        IBaseStrategyService baseStrategyService,
                                        StrategyResponseRepository strategyResponseRepository) {
        this.liveDataRepository = liveDataRepository;
        this.baseStrategyService = baseStrategyService;
        this.strategyResponseRepository = strategyResponseRepository;
    }

    @Override
    @LogPerformance
    public DayTraderStrategyResponse calculateStrategy() {
        DayTraderStrategyResponse response = new DayTraderStrategyResponse();
        List<StrategyResponseEntity> allData = strategyResponseRepository.findAll();
        response.setResponseList(this.mapper.toStrategyResponseList(allData));
        return response;
    }

    @Override
    public void saveCalculateStrategy() {
        List<StrategyResponse> strategyResponses = getStrategyResponses();
        this.saveStrategyResponse(strategyResponses);
    }

    @Override
    public void deleteCalculateStrategy() {
        strategyResponseRepository.deleteAll();
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
                if (shortTermResponse != null && middleTermResponse != null && longTermResponse != null &&
                        shortTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue()) &&
                        middleTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue()) &&
                        longTermResponse.getResponseCode().equals(ResponseCode.SUCCESS.getValue())) {
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

        futures.forEach(future -> {
            try {
                StrategyResponse strategyResponse = future.get();
                strategyResponseList.add(strategyResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();

        return strategyResponseList;
    }

    private void saveStrategyResponse(List<StrategyResponse> strategyResponses) {
        List<StrategyResponseEntity> strategyResponseEntity = this.mapper.toStrategyResponseEntity(strategyResponses);
        this.strategyResponseRepository.saveAll(strategyResponseEntity);
    }
}
