package trading.pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.pro.common.LogPerformance;
import trading.pro.dto.LoginRequest;
import trading.pro.dto.SignUpRequest;
import trading.pro.service.ILoginService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    private final ILoginService loginService;

    @Autowired
    public UserController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @LogPerformance
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginRequest loginRequest) {
        try {
            Long userId = this.loginService.login(loginRequest);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @LogPerformance
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            this.loginService.signUp(signUpRequest);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
