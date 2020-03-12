package com.trello.integration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrelloListApiResponse {
    private String id;

    private String name;

    private List<TrelloCardApiResponse> cards;
}
