package com.trello.controller;

import com.trello.model.Board;
import com.trello.model.BoardUserResponse;
import com.trello.model.FullBoard;
import com.trello.service.IBoardService;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/board")
public class BoardController {

    private final IBoardService boardService;

    @Autowired
    public BoardController(IBoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public List<BoardUserResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/board/{boardId}")
    public Board getBoardById(@Valid @PathVariable int boardId) {
        return boardService.getBoardByID(boardId);
    }

    @PostMapping("/board")
    public Board addBoard(@Valid @RequestBody Board board) {
        return boardService.addBoard(board);
    }

    @PutMapping("/board/{boardId}")
    public Board updateBoardByID(@Valid @RequestBody Board board, @Valid @PathVariable int boardId) {
        board.setBoard_id(boardId);
        return boardService.updateBoardByID(board);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity deleteBoard(@Valid @PathVariable int boardId) {
        TrelloFunctionResponse trelloFunctionResponse = boardService.deleteBoardByID(boardId);
        return new ResponseEntity(trelloFunctionResponse, HttpStatus.OK);
    }

    @GetMapping("/getFullBoard/{boardId}")
    public FullBoard getFullBoardById(@Valid @PathVariable int boardId) {
        return boardService.getFullBoardById(boardId);
    }
}
