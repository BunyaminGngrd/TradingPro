package trading.pro.service.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.dto.DayTraderStrategyResponse;
import trading.pro.dto.RequestResponseType;
import trading.pro.dto.StrategyResponse;
import trading.pro.repository.LiveDataRepository;
import trading.pro.service.strategy.IBaseStrategyService;
import trading.pro.service.strategy.IDayTraderStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public DayTraderStrategyResponse calculateStrategy() {
        DayTraderStrategyResponse response = new DayTraderStrategyResponse();
        response.setResponseList(getStrategyResponses());
        return response;
    }

    private List<StrategyResponse> getStrategyResponses() {
        List<StrategyResponse> strategyResponseList = new ArrayList<>();
        StrategyResponse strategyResponse = new StrategyResponse();

        LocalDate today = LocalDate.now();
        int shortTermPeriod = 3;
        int middleTermPeriod = 5;
        int longTermPeriod = 9;

        List<String> distinctStockCodes = liveDataRepository.findAllDistinctCodes();
        distinctStockCodes.forEach(stockCode -> {
            RequestResponseType shortTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, shortTermPeriod, today.minusDays(shortTermPeriod).toString());
            RequestResponseType middleTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, middleTermPeriod, today.minusDays(middleTermPeriod).toString());
            RequestResponseType longTermResponse = baseStrategyService.calculationOfEmaMacdAndRsiCombination(stockCode, longTermPeriod, today.minusDays(longTermPeriod).toString());

            if (shortTermResponse.getResponseCode().equals("200") && middleTermResponse.getResponseCode().equals("200") && longTermResponse.getResponseCode().equals("200")){
                strategyResponse.setStrategyResultMessage(shortTermResponse.getResponseMessage());
                strategyResponse.setBuySignal(true);
            } else {
                strategyResponse.setStrategyResultMessage("NO SIGNAL");
                strategyResponse.setBuySignal(false);
            }

            strategyResponse.setStockCode(stockCode);
            strategyResponseList.add(strategyResponse);
        });
        return strategyResponseList;
    }
}
