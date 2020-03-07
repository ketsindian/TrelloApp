package com.trello.service;

import com.trello.model.Board;
import com.trello.model.FullBoard;
import com.trello.model.FullList;
import com.trello.model.TList;
import com.trello.repository.BoardRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;
    private final IListService listService;
    private final HelperService helperService;


    @Autowired
    public BoardService(BoardRepository boardRepository, IListService listService, HelperService helperService) {
        this.boardRepository = boardRepository;
        this.listService=listService;
        this.helperService = helperService;
    }

    @Override
    public List<Board> getAllBoards() {
        List<Board> listBoards=boardRepository.getAllBoardsByUserId(1);
        if(listBoards.isEmpty())
            throw new ResourceNotFoundException("no boards not found for this user ");
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
        return boardRepository.save(board);
    }

    @Override
    public TrelloDeleteResponse deleteBoardByID(int boardId) {
        helperService.boardExistsById(boardId);
        boardRepository.deleteById(boardId);
        TrelloDeleteResponse trelloDeleteResponse = new TrelloDeleteResponse();
        trelloDeleteResponse.setMessage("board deleted successfully with id : " + boardId);
        trelloDeleteResponse.setTimestamp(LocalDateTime.now());
        return trelloDeleteResponse;
    }

    @Override
    public Board updateBoardByID(Board board) {
        helperService.boardExistsById(board.getBoard_id());
        return boardRepository.save(board);
    }

    @Override
    public FullBoard getFullBoardById(int boardId) {
        FullBoard fullBoard=new FullBoard();
        List<FullList> fullLists=new ArrayList<>();
        for (TList list:listService.getListByBoardId(boardId)) {
            fullLists.add(listService.getFullListByBoardId(boardId));
        }
        fullBoard.setBoardList(fullLists);
        return fullBoard;
    }

}
