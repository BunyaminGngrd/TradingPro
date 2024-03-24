package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CurrencyToAllResult {
    private String base;
    private String lastupdate;
    private List<CurrencyToAllResultData> data;

    @JsonCreator
    public CurrencyToAllResult(@JsonProperty("base") String base,
                               @JsonProperty("lastupdate") String lastupdate,
                               @JsonProperty("data") List<CurrencyToAllResultData> data) {
        this.base = base;
        this.lastupdate = lastupdate;
        this.data = data;
    }
}
