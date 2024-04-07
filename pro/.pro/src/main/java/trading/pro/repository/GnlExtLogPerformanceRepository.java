package trading.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.pro.entity.GnlExtLogPerformanceEntity;

@Repository
public interface GnlExtLogPerformanceRepository extends JpaRepository<GnlExtLogPerformanceEntity, Long> {
}
