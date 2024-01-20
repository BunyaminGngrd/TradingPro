package trading.pro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userentity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "user_name")
    private String userName;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "phone_number")
    private Long phoneNumber;

    @Basic
    @Column(name = "mail_")
    private String mail;

    @Basic
    @Column(name = "activation_date")
    private String activationDate;

    @Basic
    @Column(name = "password_")
    private String password;
}
