package trading.pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class DayTraderStrategyResponse {
    private List<StrategyResponse> responseList;
}
