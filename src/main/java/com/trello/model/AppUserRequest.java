package com.trello.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AppUserRequest extends AppUser {

    @NonNull
    private String password;
}
