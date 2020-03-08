package com.trello.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TrelloAuthenticationException extends RuntimeException {

    public TrelloAuthenticationException(String exception) {
        super(exception);
    }

}
