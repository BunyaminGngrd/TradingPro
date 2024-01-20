package trading.pro.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mail;
    private String password;
}
