package trading.pro.service;

import trading.pro.entity.LiveDataEntity;

import java.util.List;

public interface IBaseService {
    List<Float> extractClosingPrices(List<LiveDataEntity> stockData);
}
