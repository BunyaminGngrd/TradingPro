package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrencyToAllResult {
    private String base;
    private String lastUpdated;
    private List<CurrencyToAllResultData> resultData;

    @JsonCreator
    public CurrencyToAllResult(@JsonProperty("base") String base,
                               @JsonProperty("lastUpdated") String lastUpdated,
                               @JsonProperty("resultData") List<CurrencyToAllResultData> resultData) {
        this.base = base;
        this.lastUpdated = lastUpdated;
        this.resultData = resultData;
    }
}
