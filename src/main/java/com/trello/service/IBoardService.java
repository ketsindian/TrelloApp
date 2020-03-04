package com.trello.service;

import com.trello.model.Board;
import com.trello.utils.TrelloDeleteResponse;

public interface IBoardService {

    public Board getBoardByID(int boardId);

    public Board addBoard(Board board);

    public TrelloDeleteResponse deleteBoardByID(int boardId);

    public Board updateBoardByID(Board board);
}