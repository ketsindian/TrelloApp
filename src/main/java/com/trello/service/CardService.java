package com.trello.service;

import com.trello.model.BoardListXref;
import com.trello.model.Card;
import com.trello.model.ListCardXref;
import com.trello.repository.BoardRepository;
import com.trello.repository.CardRepository;
import com.trello.repository.ListCardXrefRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloDeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService implements  ICardService{

    private final IListService listService;
    private final CardRepository cardRepository;
    private final ListCardXrefRepository listCardXrefRepository;

    @Autowired
    public CardService(CardRepository cardRepository,IListService listService, ListCardXrefRepository listCardXrefRepository) {
        this.listService = listService;
        this.cardRepository=cardRepository;
        this.listCardXrefRepository=listCardXrefRepository;
    }

    @Override
    public List<Card> getCardByListId(int boardId,int listId) {
        this.checkBoardListExists(boardId,listId);
        List<Card> cards=cardRepository.getCardByListId(listId);
        if(cards.isEmpty())
            throw new ResourceNotFoundException("No card found with list : "+listId);
        return cards;
    }

    @Override
    public Card getCardByListIdCardId(int boardId,ListCardXref listCardXref) {
        this.checkBoardListExists(boardId,listCardXref.getList_id());
        Card card=cardRepository.getCardByListIdCardId(listCardXref.getList_id(),listCardXref.getCard_id());
        if(card==null)
            throw new ResourceNotFoundException("Card Not found by List : "+listCardXref.getList_id()+" and card : "+listCardXref.getCard_id());
        return card;
    }

    @Transactional
    @Override
    public Card addCardByListId(int boardId,int listId, Card card) {
        this.checkBoardListExists(boardId,listId);
        Card cardAdded=cardRepository.save(card);
        ListCardXref listCardXref=new ListCardXref();
        listCardXref.setCard_id(cardAdded.getCard_id());
        listCardXref.setList_id(listId);
        ListCardXref listCardXrefAdded=listCardXrefRepository.save(listCardXref);
        return cardAdded;
    }

    @Override
    public TrelloDeleteResponse deleteCardByListIdCardId(ListCardXref listCardXref) {
        if(!this.listExistsByListIdCardId(listCardXref))
            throw new ResourceNotFoundException("No card with id : "+listCardXref.getCard_id()+" found in list with id "+listCardXref.getList_id());
        cardRepository.deleteById(listCardXref.getCard_id());
        TrelloDeleteResponse deleteResponse=new TrelloDeleteResponse();
        deleteResponse.setTimestamp(LocalDateTime.now());
        deleteResponse.setMessage("card : "+listCardXref.getCard_id()+" removed successfully from list : "+listCardXref.getList_id());
        return deleteResponse;
    }

    @Override
    public Card updateCardByListIdCardId(int boardId,int listId, Card card) {
        this.checkBoardListExists(boardId,listId);
        ListCardXref listCardXref=new ListCardXref();
        listCardXref.setList_id(listId);
        listCardXref.setCard_id(card.getCard_id());
        if(!this.listExistsByListIdCardId(listCardXref))
            throw new ResourceNotFoundException("No card with id : "+listCardXref.getCard_id()+" found in list with id "+listCardXref.getList_id());
        return cardRepository.save(card);
    }

    @Override
    public boolean listExistsByListIdCardId(ListCardXref listCardXref) {
        Example<ListCardXref> example = Example.of(listCardXref, ExampleMatcher.matchingAll());;
        return listCardXrefRepository.exists(example);
    }

    private void checkBoardListExists(int boardId,int listId){
        listService.checkBoardExists(boardId);
        BoardListXref boardListXref=new BoardListXref();
        boardListXref.setList_id(listId);
        boardListXref.setBoard_id(boardId);
        if(!listService.listExistsByBoardIdListId(boardListXref))
            throw new ResourceNotFoundException("List with id :"+listId+"does not exist under Board with id : "+boardId);
    }
}
