package trading.pro.service;

import trading.pro.dto.LoginRequest;
import trading.pro.dto.SignUpRequest;

public interface ILoginService {
    Long login(LoginRequest loginRequest);

    void signUp(SignUpRequest signUpRequest);
}
