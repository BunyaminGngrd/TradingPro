package trading.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.pro.entity.StrategyResponseEntity;

@Repository
public interface StrategyResponseRepository extends JpaRepository<StrategyResponseEntity, Long> {
}
