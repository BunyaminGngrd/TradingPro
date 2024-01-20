package trading.pro.dto;

import lombok.Data;

import java.util.List;

@Data
public class MacdCalculateResponse {
    private List<Float> macdLine;
    private List<Float> signalLine;
    private List<Float> histogram;
}
