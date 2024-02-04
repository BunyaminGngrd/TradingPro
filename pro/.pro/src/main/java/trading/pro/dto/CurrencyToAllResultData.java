package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyToAllResultData {
    private String code;
    private String name;
    private Double rate;
    private String calculatestr;
    private Double calculated;

    @JsonCreator
    public CurrencyToAllResultData(@JsonProperty("code") String code,
                          @JsonProperty("name") String name,
                          @JsonProperty("rate") double rate,
                          @JsonProperty("calculatedstr")String calculatestr,
                          @JsonProperty("calculated") double calculated) {
        this.code = code;
        this.name = name;
        this.rate = rate;
        this.calculatestr = calculatestr;
        this.calculated = calculated;
    }
}
