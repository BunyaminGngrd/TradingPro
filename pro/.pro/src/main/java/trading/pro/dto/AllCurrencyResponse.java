package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllCurrencyResponse {
    private String success;
    private List<AllCurrencyResult> result;

    @JsonCreator
    public AllCurrencyResponse(@JsonProperty("success") String success,
                               @JsonProperty("result") List<AllCurrencyResult> result) {
        this.success = success;
        this.result = result;
    }
}
