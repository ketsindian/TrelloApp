package com.trello.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrelloFunctionResponse {

    private String message;

    private LocalDateTime timestamp;

    private String details;
}
