package trading.pro.service.indicator;

public interface IRsiCalculatorService {
    Double calculateRsiForStock(String stockCode, int period, String startDate);
}
