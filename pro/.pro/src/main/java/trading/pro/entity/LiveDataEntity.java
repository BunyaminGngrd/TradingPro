package trading.pro.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "liveData")
public class LiveDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "live_data_id")
    private Long liveDataId;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "last_price")
    private Float lastPrice;

    @Column(name = "last_price_str")
    private String lastPriceStr;

    @Column(name = "hacim")
    private Double hacim;

    @Column(name = "hacim_str")
    private String hacimStr;

    @Column(name = "min")
    private Float min;

    @Column(name = "min_str")
    private String minStr;

    @Column(name = "max")
    private Float max;

    @Column(name = "max_str")
    private String maxStr;

    @Column(name = "text")
    private String text;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private String date;
}
