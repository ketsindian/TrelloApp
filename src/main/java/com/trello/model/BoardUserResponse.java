package com.trello.model;

import com.trello.repository.BoardRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BoardUserResponse extends Board {

    public BoardUserResponse(Board board, USER_TYPE user_type ){
        this.board_id=board.getBoard_id();
        this.board_name=board.getBoard_name();
        this.board_owner_id=board.getBoard_owner_id();
        this.user_type=user_type;
    };

    private  USER_TYPE user_type;
}
