package trading.pro.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "strategy_response")
public class StrategyResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_response_id")
    private Long strategyResponseId;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "strategy_result_message")
    private String strategyResultMessage;

    @Column(name = "buy_signal")
    private Boolean buySignal;

    @Column(name = "date")
    private Date date;
}
