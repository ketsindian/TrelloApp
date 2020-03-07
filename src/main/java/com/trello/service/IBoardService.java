package com.trello.service;

import com.trello.model.Board;
import com.trello.model.FullBoard;
import com.trello.utils.TrelloDeleteResponse;

import java.util.List;

public interface IBoardService {

    public List<Board> getAllBoards();

    public Board getBoardByID(int boardId);

    public Board addBoard(Board board);

    public TrelloDeleteResponse deleteBoardByID(int boardId);

    public Board updateBoardByID(Board board);

    public FullBoard getFullBoardById(int boardId);

}