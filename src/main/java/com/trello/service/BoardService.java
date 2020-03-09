package com.trello.service;

import com.trello.model.*;
import com.trello.repository.BoardRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;
    private final IListService listService;
    private final HelperService helperService;

    @Autowired
    public BoardService(BoardRepository boardRepository, IListService listService, HelperService helperService) {
        this.boardRepository = boardRepository;
        this.listService = listService;
        this.helperService = helperService;
    }

    @Override
    public List<BoardUserResponse> getAllBoards() {
        final int userId = helperService.getUserFromContext().getUser_id();
        List<BoardUserResponse> listBoards = boardRepository.getAllBoardsByOwnerId(userId).stream()
                .map(x->new BoardUserResponse(x,USER_TYPE.OWNER)).collect(Collectors.toList());
        if (listBoards.isEmpty())
            throw new ResourceNotFoundException("no boards not found for this user ");
        List<BoardUserResponse> listBoardsSecUsers = boardRepository.getBoardsBySecUserId(userId).stream().
                map(x->new BoardUserResponse(x,USER_TYPE.SECONDARY_USER)).collect(Collectors.toList());
        listBoards.addAll(listBoardsSecUsers);
        return listBoards;
    }

    @Override
    public Board getBoardByID(int boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent())
            return board.get();
        else
            throw new ResourceNotFoundException("board not found with id : " + boardId);
    }

    @Override
    public Board addBoard(Board board) {
        board.setBoard_owner_id(helperService.getUserFromContext().getUser_id());
        return boardRepository.save(board);
    }

    @Override
    public TrelloFunctionResponse deleteBoardByID(int boardId) {
        helperService.boardExistsById(boardId);
        boardRepository.deleteById(boardId);
        TrelloFunctionResponse trelloFunctionResponse = new TrelloFunctionResponse();
        trelloFunctionResponse.setMessage("board deleted successfully with id : " + boardId);
        trelloFunctionResponse.setTimestamp(LocalDateTime.now());
        return trelloFunctionResponse;
    }

    @Override
    public Board updateBoardByID(Board board) {
        helperService.boardExistsById(board.getBoard_id());
        return boardRepository.save(board);
    }

    @Override
    public FullBoard getFullBoardById(int boardId) {
        FullBoard fullBoard = new FullBoard();
        List<FullList> fullLists = new ArrayList<>();
        for (TList list : listService.getListByBoardId(boardId)) {
            fullLists.add(listService.getFullListByBoardId(boardId));
        }
        fullBoard.setBoardList(fullLists);
        return fullBoard;
    }

}
