package trading.pro.dto;

import lombok.Data;

@Data
public class ConvertToDesiredCurrencyResponse {
    private String code;
    private String name;
    private Double rate;
    private String calculatestr;
    private Double calculated;
}
