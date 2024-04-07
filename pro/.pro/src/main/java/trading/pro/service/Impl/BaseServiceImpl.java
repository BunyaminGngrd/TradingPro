package trading.pro.service.Impl;

import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.entity.LiveDataEntity;
import trading.pro.service.IBaseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseServiceImpl implements IBaseService {
    @LogPerformance
    @Override
    public List<Float> extractClosingPrices(List<LiveDataEntity> stockData) {
        List<Float> closingPrices = new ArrayList<>();
        for (LiveDataEntity data : stockData) {
            closingPrices.add(data.getLastPrice());
        }
        return closingPrices;
    }
}
