package trading.pro.service.Impl;

import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.dto.RequestResponseType;
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
        stockData.stream().map(data -> closingPrices.add(data.getLastPrice()));
        return closingPrices;
    }

    @Override
    public RequestResponseType createResponseMessage(String responseCode, String responseMessage, String response) {
        RequestResponseType requestResponseType = new RequestResponseType();
        requestResponseType.setResponseCode(responseCode);
        requestResponseType.setResponseMessage(responseMessage);
        requestResponseType.setResponse(response);
        return requestResponseType;
    }

    @Override
    public RequestResponseType createResponseMessage(String responseCode, String responseMessage) {
        RequestResponseType requestResponseType = new RequestResponseType();
        requestResponseType.setResponseCode(responseCode);
        requestResponseType.setResponseMessage(responseMessage);
        return requestResponseType;
    }
}
