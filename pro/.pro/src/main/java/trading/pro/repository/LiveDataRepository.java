package trading.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trading.pro.entity.LiveDataEntity;

import java.util.List;

@Repository
public interface LiveDataRepository extends JpaRepository<LiveDataEntity, Long> {

    @Query("SELECT e FROM liveData e WHERE e.code = :code ORDER BY e.date DESC LIMIT 1")
    LiveDataEntity findByCode(String code);


    List<LiveDataEntity> findByCodeAndDateAfter(String code, String date);

    @Query("SELECT DISTINCT e.code FROM liveData e")
    List<String> findAllDistinctCodes();
}
