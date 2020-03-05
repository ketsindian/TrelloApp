package com.trello.controller;

import com.trello.model.BoardListXref;
import com.trello.model.TList;
import com.trello.service.IListService;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("board/{boardId}")
public class ListController {
    private final IListService listService;

    @Autowired
    public ListController(IListService listService) {
        this.listService = listService;
    }

    @GetMapping("/list")
    public List<TList> getListByBoardId(@Valid @PathVariable int boardId) {
        return listService.getListByBoardId(boardId);
    }

    @GetMapping("/list/{listId}")
    public TList getListByBoardIdListId(@Valid @PathVariable int boardId , @Valid @PathVariable int listId) {
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setBoard_id(boardId);
        boardListXref.setList_id(listId);
        return listService.getListByBoardIdListId(boardListXref);
    }

    @PostMapping("/list")
    public TList addListToBoard(@Valid @PathVariable int boardId , @Valid @RequestBody TList list) {
        return listService.addListByBoardId(boardId,list);
    }

    @PutMapping("/list/{listId}")
    public TList updateListByListIdBoardId(@Valid @PathVariable int boardId , @Valid @RequestBody TList list, @Valid @PathVariable int listId) {
        list.setList_id(listId);
        return listService.updateListByBoardIdListId(boardId,list);
    }

    @DeleteMapping("/list/{listId}")
    public ResponseEntity deleteListByBoardIdListId(@Valid @PathVariable int boardId ,@Valid @PathVariable int listId) {
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setBoard_id(boardId);
        boardListXref.setList_id(listId);
        TrelloDeleteResponse trelloDeleteResponse = listService.deleteListByBoardIdListId(boardListXref);
        return new ResponseEntity(trelloDeleteResponse, HttpStatus.OK);
    }
}
