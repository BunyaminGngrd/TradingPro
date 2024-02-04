package trading.pro.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import trading.pro.dto.*;

public interface ICurrencyToAllService {
    CurrencyToAllResponseDTO currencyToAll(CurrencyToAllRequest currencyToAllRequest);
    ConvertToDesiredCurrencyResponse convertToDesiredCurrency(ConvertToDesiredCurrencyRequest convertToDesiredCurrencyRequest);
    AllCurrencyResponse allCurrency();
}
