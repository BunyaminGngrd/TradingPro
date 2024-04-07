package trading.pro.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "gnl_ext_log_performance")
public class GnlExtLogPerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gnl_ext_log_performance_id")
    private Long gnlExtId;

    @Column(name = "oper_name")
    private String operName;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "execution_time")
    private long executionTime;
}
