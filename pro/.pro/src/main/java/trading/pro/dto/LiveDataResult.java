package trading.pro.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LiveDataResult {
    private float rate;
    private float lastPrice;
    private String lastPriceStr;
    private double hacim;
    private String hacimStr;
    private float min;
    private String minStr;
    private float max;
    private String maxStr;
    private String time;
    private String text;
    private String code;


    @JsonCreator
    public LiveDataResult(@JsonProperty("rate") float rate,
                          @JsonProperty("lastprice") float lastPrice,
                          @JsonProperty("lastpricestr")String lastPriceStr,
                          @JsonProperty("hacim")double hacim,
                          @JsonProperty("hacimstr")String hacimStr,
                          @JsonProperty("min")float min,
                          @JsonProperty("minstr")String minStr,
                          @JsonProperty("max")float max,
                          @JsonProperty("maxstr")String maxStr,
                          @JsonProperty("time")String time,
                          @JsonProperty("text")String text,
                          @JsonProperty("code")String code ) {
        this.rate = rate;
        this.lastPrice = lastPrice;
        this.lastPriceStr = lastPriceStr;
        this.hacim = hacim;
        this.hacimStr = hacimStr;
        this.min = min;
        this.minStr = minStr;
        this.max = max;
        this.maxStr = maxStr;
        this.time = time;
        this.text = text;
        this.code = code;
    }
}
