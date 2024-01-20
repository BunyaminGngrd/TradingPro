package trading.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trading.pro.dto.User;
import trading.pro.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByUserName(String userName);
    UserEntity findByMail(String mail);

}
