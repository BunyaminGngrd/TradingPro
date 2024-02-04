package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AllCurrencyResult {
    private String name;
    private String buying;
    private String selling;

    @JsonCreator
    public AllCurrencyResult(@JsonProperty("name") String name,
                             @JsonProperty("buying") String buying,
                             @JsonProperty("selling") String selling) {
        this.name = name;
        this.buying = buying;
        this.selling = selling;
    }
}
