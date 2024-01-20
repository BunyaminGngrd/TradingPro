package trading.pro.dto;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String mail;
    private String activationDate;
}
