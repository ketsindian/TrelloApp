package com.trello.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "user_d")
public class AppUser {

    @Id
    @GeneratedValue
    private int user_id;

    private String first_name;

    private String last_name;

    @NonNull
    private String email_id;

    private String phone_number;

    @NonNull
    private String login_id;

    public void extractFromUSerRequest(AppUserRequest userRequest) {
        this.user_id = userRequest.getUser_id();
        this.first_name = userRequest.getFirst_name();
        this.last_name = userRequest.getLast_name();
        this.email_id = userRequest.getEmail_id();
        this.phone_number = userRequest.getPhone_number();
        this.login_id = userRequest.getLogin_id();
    }

}
