package trading.pro.dto;

import lombok.Data;

@Data
public class IndicatorRequestDTO {
   private String stockCode;
    private int period;
    private String startDate;
}
