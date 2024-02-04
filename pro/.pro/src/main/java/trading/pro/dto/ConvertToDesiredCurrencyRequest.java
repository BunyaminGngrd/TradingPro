package trading.pro.dto;

import lombok.Data;

@Data
public class ConvertToDesiredCurrencyRequest {
    private String currency;
    private int amount;
    private String desiredCurrency;
}
