package trading.pro.dto;

import lombok.Data;

@Data
public class StrategyResponse {
    private String stockCode;
    private String strategyResultMessage;
    private Boolean buySignal;
}
