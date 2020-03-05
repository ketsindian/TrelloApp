package com.trello.service;

import com.trello.model.Board;
import com.trello.model.BoardListXref;
import com.trello.model.TList;
import com.trello.repository.BoardListXrefRepository;
import com.trello.repository.BoardRepository;
import com.trello.repository.ListRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListService implements IListService{

    private final ListRepository listRepository;
    private final BoardListXrefRepository boardListXrefRepository;
    private final IBoardService boardService;

    @Autowired
    public ListService(ListRepository listRepository, BoardListXrefRepository boardListXrefRepository,IBoardService boardService) {
        this.listRepository = listRepository;
        this.boardListXrefRepository=boardListXrefRepository;
        this.boardService=boardService;
    }

    @Override
    public List<TList> getListByBoardId(int boardId) {
        List<TList> list =listRepository.getListByBoardId(boardId);
        if(list.isEmpty())
            throw new ResourceNotFoundException("No List found with board : "+boardId);
        return list;
    }

    @Override
    public TList getListByBoardIdListId(BoardListXref boardListXref) {
        TList list=listRepository.getListByBoardIdListId(boardListXref.getBoard_id(),boardListXref.getList_id());
        if(list==null)
            throw new ResourceNotFoundException("List Not found by Board : "+boardListXref.getBoard_id()+" and list : "+boardListXref.getList_id());
        return list;
    }

    @Override
    @Transactional
    public TList addListByBoardId(int boardId,TList list) {
        if(!boardService.boardExistsById(boardId))
            throw new ResourceNotFoundException("board not found with id : " + boardId);
        TList listAdded=listRepository.save(list);
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setList_id(listAdded.getList_id());
        boardListXref.setBoard_id(boardId);
        BoardListXref boardListXrefAdded= boardListXrefRepository.save(boardListXref);
        return listAdded;
    }

    @Override
    public TrelloDeleteResponse deleteListByBoardIdListId(BoardListXref boardListXref) {
        if(!this.listExistsByBoardIdListId(boardListXref))
            throw new ResourceNotFoundException("No list with id : "+boardListXref.getList_id()+" found in board with id "+boardListXref.getBoard_id());
        listRepository.deleteById(boardListXref.getList_id());
        TrelloDeleteResponse deleteResponse=new TrelloDeleteResponse();
        deleteResponse.setTimestamp(LocalDateTime.now());
        deleteResponse.setMessage("list : "+boardListXref.getList_id()+" removed successfully from board : "+boardListXref.getBoard_id());
        return deleteResponse;
    }

    @Override
    public TList updateListByBoardIdListId(int boardId,TList list) {
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setBoard_id(boardId);
        boardListXref.setList_id(list.getList_id());
        if(!this.listExistsByBoardIdListId(boardListXref))
            throw new ResourceNotFoundException("No list with id : "+boardListXref.getList_id()+" found in board with id "+boardListXref.getBoard_id());
        return listRepository.save(list);
    }

    @Override
    public boolean listExistsByBoardIdListId(BoardListXref boardListXref) {
         Example<BoardListXref> example = Example.of(boardListXref, ExampleMatcher.matchingAll());;
         return boardListXrefRepository.exists(example);
    }
}
