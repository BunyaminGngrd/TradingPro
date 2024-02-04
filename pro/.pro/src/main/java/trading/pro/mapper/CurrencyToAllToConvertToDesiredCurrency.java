package trading.pro.mapper;

import org.mapstruct.Mapper;
import trading.pro.dto.ConvertToDesiredCurrencyResponse;
import trading.pro.dto.CurrencyToAllResultData;

@Mapper(componentModel = "spring")
public interface CurrencyToAllToConvertToDesiredCurrency {
    ConvertToDesiredCurrencyResponse toConvertToDesiredCurrencyResponse(CurrencyToAllResultData currency);
}
