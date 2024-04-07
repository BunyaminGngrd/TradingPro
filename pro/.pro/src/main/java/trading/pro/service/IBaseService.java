package trading.pro.service;

import trading.pro.dto.RequestResponseType;
import trading.pro.entity.LiveDataEntity;

import java.util.List;

public interface IBaseService {
    List<Float> extractClosingPrices(List<LiveDataEntity> stockData);

    RequestResponseType createResponseMessage(String responseCode, String responseMessage, String response);

    RequestResponseType createResponseMessage(String responseCode, String responseMessage);

    RequestResponseType createResponseMessage(String responseCode);
}
