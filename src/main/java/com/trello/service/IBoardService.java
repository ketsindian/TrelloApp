package com.trello.service;

import com.trello.model.*;
import com.trello.utils.TrelloFunctionResponse;

import java.util.List;

public interface IBoardService {

    public List<BoardUserResponse> getAllBoards();

    public Board getBoardByID(int boardId);

    public BoardUserResponse addBoard(Board board);

    public TrelloFunctionResponse deleteBoardByID(int boardId);

    public Board updateBoardByID(Board board);

    public FullBoard getFullBoardById(int boardId);

    public List<AppUser> getSecUsersByBoardId(int boardId);

    public List<AppUser> getUnsharedUsersByBoardId(int boardId);

    public BoardUserXref shareBoard(BoardUserXref boardUserXref);

    public TrelloFunctionResponse unShareBoard(BoardUserXref boardUserXref);

}