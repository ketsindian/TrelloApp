package com.trello.service;

import com.trello.model.Board;
import com.trello.repository.BoardRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService implements IBoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
        if (boardRepository.existsById(boardId))
            boardRepository.deleteById(boardId);
        else
            throw new ResourceNotFoundException("board not found with id : " + boardId);
        TrelloDeleteResponse trelloDeleteResponse = new TrelloDeleteResponse();
        trelloDeleteResponse.setMessage("board deleted successfully with id : " + boardId);
        trelloDeleteResponse.setTimestamp(LocalDateTime.now());
        return trelloDeleteResponse;
    }

    @Override
    public Board updateBoardByID(Board board) {
        if (boardRepository.existsById(board.getBoard_id()))
            return boardRepository.save(board);
        else
            throw new ResourceNotFoundException("board not found with id : " + board.getBoard_id());
    }
}
