package trading.pro.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trading.pro.entity.GnlExtLogPerformanceEntity;
import trading.pro.repository.GnlExtLogPerformanceRepository;

@Aspect
@Component
public class PerformanceAspect {
    private final GnlExtLogPerformanceRepository gnlExtLogPerformanceRepository;
    private long startTime;

    @Autowired
    public PerformanceAspect(GnlExtLogPerformanceRepository gnlExtLogPerformanceRepository) {
        this.gnlExtLogPerformanceRepository = gnlExtLogPerformanceRepository;
    }

    @Before("@annotation(LogPerformance)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @After("@annotation(LogPerformance)")
    public void afterMethodExecution(JoinPoint joinPoint) {
        GnlExtLogPerformanceEntity entity = new GnlExtLogPerformanceEntity();
        long endTime = System.currentTimeMillis();

        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setExecutionTime(endTime - startTime);
        entity.setOperName(joinPoint.getSignature().getName());

        gnlExtLogPerformanceRepository.save(entity);
    }
}
