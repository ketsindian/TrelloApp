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
public class TrelloBoardApiResponse {

    private List<TrelloListApiResponse> lists;
}
