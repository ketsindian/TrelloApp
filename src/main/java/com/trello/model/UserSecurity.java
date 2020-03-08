package com.trello.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "user_security_d")
@IdClass(UserSecurity.class)

public class UserSecurity implements Serializable {

    @Id
    @PositiveOrZero
    private int user_id;

    @Id
    private String password;

    public void extractUserSecurityFormUserRequest(AppUserRequest userRequest) {
        this.user_id = userRequest.getUser_id();
        this.password = new BCryptPasswordEncoder().encode(userRequest.getPassword());
    }
}
