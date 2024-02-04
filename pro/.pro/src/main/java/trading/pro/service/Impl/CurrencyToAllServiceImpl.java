package trading.pro.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import trading.pro.dto.*;
import trading.pro.mapper.CurrencyToAllToConvertToDesiredCurrency;
import trading.pro.service.ICurrencyToAllService;

import java.util.List;
import java.util.Objects;

@Service
public class CurrencyToAllServiceImpl implements ICurrencyToAllService {
    private final String apiKey;
    private final CurrencyToAllToConvertToDesiredCurrency mapper = Mappers.getMapper(CurrencyToAllToConvertToDesiredCurrency.class);

    @Autowired
    public CurrencyToAllServiceImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public CurrencyToAllResponseDTO currencyToAll(CurrencyToAllRequest currencyToAllRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        String intParamValue = String.valueOf(currencyToAllRequest.getAmount());
        String baseParamValue = currencyToAllRequest.getCurrency();

        try {
            HttpResponse<String> response = Unirest.get("https://api.collectapi.com/economy/currencyToAll")
                    .queryString("int", intParamValue)
                    .queryString("base", baseParamValue)
                    .header("content-type", "application/json")
                    .header("authorization", apiKey)
                    .asString();

            CurrencyToAllResponseDTO currencyToAllResponseDTO = objectMapper.readValue(response.getBody(), CurrencyToAllResponseDTO.class);
            return currencyToAllResponseDTO;
        } catch (Exception ex) {
            throw new RuntimeException("CurrencyToAllServiceImpl.currencyToAll().ERROR : " + ex.getMessage());
        }
    }

    @Override
    public ConvertToDesiredCurrencyResponse convertToDesiredCurrency(ConvertToDesiredCurrencyRequest convertToDesiredCurrencyRequest) {
        try {
            CurrencyToAllRequest currencyToAllRequest = new CurrencyToAllRequest();
            currencyToAllRequest.setCurrency(convertToDesiredCurrencyRequest.getCurrency());
            currencyToAllRequest.setAmount(convertToDesiredCurrencyRequest.getAmount());
            CurrencyToAllResponseDTO currencyToAllResponseDTO = currencyToAll(currencyToAllRequest);

            List<CurrencyToAllResultData> resultDataList = currencyToAllResponseDTO.getResult().getResultData();

            CurrencyToAllResultData desiredResult = resultDataList.stream()
                    .filter(currency -> currency.getCode().equals(convertToDesiredCurrencyRequest.getDesiredCurrency()))
                    .findFirst()
                    .orElse(null);

            if (!Objects.isNull(desiredResult)) {
                return this.mapper.toConvertToDesiredCurrencyResponse(desiredResult);
            }

        } catch (Exception e) {
            throw new RuntimeException("CurrencyToAllServiceImpl.convertToDesiredCurrency().ERROR : " + e.getMessage());
        }

        return null;
    }

    @Override
    public AllCurrencyResponse allCurrency() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpResponse<String> response = Unirest.get("https://api.collectapi.com/economy/allCurrency")
                    .header("content-type", "application/json")
                    .header("authorization", apiKey)
                    .asString();

            AllCurrencyResponse allCurrencyResponse = objectMapper.readValue(response.getBody(), AllCurrencyResponse.class);
            return allCurrencyResponse;
        } catch (Exception ex) {
            throw new RuntimeException("CollectAPIServiceImpl.getAllData().ERROR : " + ex.getMessage());
        }
    }
}
