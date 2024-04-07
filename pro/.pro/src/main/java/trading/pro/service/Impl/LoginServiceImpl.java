package trading.pro.service.Impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trading.pro.common.LogPerformance;
import trading.pro.dto.LoginRequest;
import trading.pro.dto.SignUpRequest;
import trading.pro.entity.UserEntity;
import trading.pro.mapper.UserMapper;
import trading.pro.repository.UserRepository;
import trading.pro.service.ILoginService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class LoginServiceImpl implements ILoginService {
    private final UserRepository userRepository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @LogPerformance
    @Override
    public Long login(LoginRequest loginRequest) {
        try {
            if (!Objects.isNull(loginRequest)) {
                UserEntity user = this.userRepository.findByUserName(loginRequest.getUserName());
                if (!Objects.isNull(user)) {
                    boolean passwordCheck = loginRequest.getPassword().equals(user.getPassword());
                    if (passwordCheck) {
                        return user.getUserId();
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("LoginServiceImpl.login.ERROR : " + ex.getMessage());
        }
        return null;
    }

    @LogPerformance
    @Override
    public void signUp(SignUpRequest signUpRequest) {
        try {
            if (!Objects.isNull(signUpRequest)) {
                // Aynı e-posta adresine sahip bir kullanıcının zaten var olup olmadığını kontrol et
                if (userRepository.findByMail(signUpRequest.getMail()) != null) {
                    throw new RuntimeException("Aynı e-posta adresine sahip kullanıcı zaten mevcut");
                }

                LocalDateTime now = LocalDateTime.now();
                String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                UserEntity user = this.userMapper.fromSignUpRequest(signUpRequest);
                user.setActivationDate(formattedDate);

                this.userRepository.save(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException("LoginServiceImpl.signUp.HATA: " + ex.getMessage());
        }
    }
}
