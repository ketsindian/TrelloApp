package com.trello.service;

import com.trello.model.*;
import com.trello.repository.BoardListXrefRepository;
import com.trello.repository.BoardRepository;
import com.trello.repository.ListRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListService implements IListService{

    private final ListRepository listRepository;
    private final BoardListXrefRepository boardListXrefRepository;
    private final ICardService cardService;
    private final HelperService helperService;

    @Autowired
    public ListService(ListRepository listRepository, BoardListXrefRepository boardListXrefRepository, ICardService cardService, HelperService helperService) {
        this.listRepository = listRepository;
        this.boardListXrefRepository=boardListXrefRepository;
        this.cardService=cardService;
        this.helperService = helperService;
    }

    @Override
    public List<TList> getListByBoardId(int boardId) {
        helperService.boardExistsById(boardId);
        List<TList> list =listRepository.getListByBoardId(boardId);
        if(list.isEmpty())
            throw new ResourceNotFoundException("No List found with board : "+boardId);
        return list;
    }

    @Override
    public TList getListByBoardIdListId(BoardListXref boardListXref) {
        helperService.boardExistsById(boardListXref.getBoard_id());
        TList list=listRepository.getListByBoardIdListId(boardListXref.getBoard_id(),boardListXref.getList_id());
        if(list==null)
            throw new ResourceNotFoundException("List Not found by Board : "+boardListXref.getBoard_id()+" and list : "+boardListXref.getList_id());
        return list;
    }

    @Override
    @Transactional
    public TList addListByBoardId(int boardId,TList list) {
        helperService.boardExistsById(boardId);
        TList listAdded=listRepository.save(list);
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setList_id(listAdded.getList_id());
        boardListXref.setBoard_id(boardId);
        BoardListXref boardListXrefAdded= boardListXrefRepository.save(boardListXref);
        return listAdded;
    }

    @Override
    public TrelloDeleteResponse deleteListByBoardIdListId(BoardListXref boardListXref) {
        helperService.listExistsByBoardIdListId(boardListXref);
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
        helperService.listExistsByBoardIdListId(boardListXref);
        return listRepository.save(list);
    }


    @Override
    public FullList getFullListByBoardId(int boardId) {
        FullList fullList=new FullList();
        List<FullCards> fullCardsList=new ArrayList<>();
        for (TList list:this.getListByBoardId(boardId)) {
            FullCards fullCards=new FullCards();
            fullCards.setCards(cardService.getCardByListId(boardId,list.getList_id()));
            fullCardsList.add(fullCards);
        }
        fullList.setListCards(fullCardsList);
        return fullList;
    }

}
