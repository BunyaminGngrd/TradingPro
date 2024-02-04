package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyToAllResponseDTO {
    private String success;
    private CurrencyToAllResult result;

    @JsonCreator
    public CurrencyToAllResponseDTO(@JsonProperty("success") String success,
                                    @JsonProperty("result") CurrencyToAllResult result) {
        this.success = success;
        this.result = result;
    }
}
