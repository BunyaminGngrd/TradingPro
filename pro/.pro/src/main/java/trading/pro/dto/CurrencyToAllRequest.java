package trading.pro.dto;

import lombok.Data;

@Data
public class CurrencyToAllRequest {
    private String currency;
    private int amount;
}
