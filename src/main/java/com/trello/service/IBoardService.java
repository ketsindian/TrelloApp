package com.trello.service;

import com.trello.model.Board;
import com.trello.model.FullBoard;
import com.trello.utils.TrelloFunctionResponse;

import java.util.List;

public interface IBoardService {

    public List<Board> getAllBoards();

    public Board getBoardByID(int boardId);

    public Board addBoard(Board board);

    public TrelloFunctionResponse deleteBoardByID(int boardId);

    public Board updateBoardByID(Board board);

    public FullBoard getFullBoardById(int boardId);

}