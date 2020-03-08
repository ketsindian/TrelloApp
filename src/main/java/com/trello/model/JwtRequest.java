package com.trello.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JwtRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;

}
