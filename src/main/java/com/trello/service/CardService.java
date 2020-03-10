package com.trello.service;

import com.trello.model.Card;
import com.trello.model.CardResponse;
import com.trello.model.ListCardXref;
import com.trello.repository.CardRepository;
import com.trello.repository.ListCardXrefRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CardService implements ICardService {

    private final CardRepository cardRepository;
    private final ListCardXrefRepository listCardXrefRepository;
    private final HelperService helperService;

    @Autowired
    public CardService(CardRepository cardRepository, ListCardXrefRepository listCardXrefRepository, HelperService helperService) {
        this.cardRepository = cardRepository;
        this.listCardXrefRepository = listCardXrefRepository;
        this.helperService = helperService;
    }

    @Override
    public List<CardResponse> getCardByListId(int boardId, int listId) {
        helperService.listExistsByBoardIdListId(boardId, listId);
        List<Card> cards = cardRepository.getCardByListId(listId);
        if (cards.isEmpty())
            throw new ResourceNotFoundException("No card found with list : " + listId);
        List<ListCardXref> listCardXref=listCardXrefRepository.getListCardXrefByListId(listId);
        cards.sort(Comparator.comparing(Card::getCard_id));
        listCardXref.sort(Comparator.comparing(ListCardXref::getCard_id));
        List<CardResponse> cardResponseList=new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            cardResponseList.add(new CardResponse(cards.get(i),listCardXref.get(i).getCard_priority_id()));
        }
        return cardResponseList;
    }

    @Override
    public Card getCardByListIdCardId(int boardId, ListCardXref listCardXref) {
        helperService.listExistsByBoardIdListId(boardId, listCardXref.getList_id());
        Card card = cardRepository.getCardByListIdCardId(listCardXref.getList_id(), listCardXref.getCard_id());
        if (card == null)
            throw new ResourceNotFoundException("Card Not found by List : " + listCardXref.getList_id() + " and card : " + listCardXref.getCard_id());
        return card;
    }

    @Transactional
    @Override
    public CardResponse addCardByListId(int boardId, int listId, Card card) {
        helperService.listExistsByBoardIdListId(boardId, listId);
        Card cardAdded = cardRepository.save(card);
        ListCardXref listCardXref = new ListCardXref();
        listCardXref.setList_id(listId);
//        find matching count of listid cardid for example
        Example<ListCardXref> example= Example.of(listCardXref,ExampleMatcher.matchingAny());
        long nextPriority=listCardXrefRepository.count(example);
        listCardXref.setCard_priority_id((int)nextPriority+1);
        listCardXref.setCard_id(cardAdded.getCard_id());
        ListCardXref listCardXrefAdded = listCardXrefRepository.save(listCardXref);
        return new CardResponse(cardAdded,listCardXref.getCard_priority_id());
    }

    @Transactional
    @Override
    public TrelloFunctionResponse deleteCardByListIdCardId(ListCardXref listCardXref) {
//        helperService.cardExistsByListIdCardId(listCardXref);
        ListCardXref listCardXrefFound = this.getByListIdCardId(listCardXref);
        cardRepository.deleteById(listCardXrefFound.getCard_id());
        updateCardPrioritiesOnDelete(listCardXrefFound);
        TrelloFunctionResponse deleteResponse = new TrelloFunctionResponse();
        deleteResponse.setTimestamp(LocalDateTime.now());
        deleteResponse.setMessage("card : " + listCardXrefFound.getCard_id() + " removed successfully from list : " + listCardXrefFound.getList_id());
        return deleteResponse;
    }

    @Override
    public Card updateCardByListIdCardId(int boardId, int listId, Card card) {
        helperService.listExistsByBoardIdListId(boardId, listId);
        ListCardXref listCardXref = new ListCardXref();
        listCardXref.setList_id(listId);
        listCardXref.setCard_id(card.getCard_id());
        helperService.cardExistsByListIdCardId(listCardXref);
        return cardRepository.save(card);
    }

    private boolean updateCardPrioritiesOnDelete(ListCardXref listCardXref){
        List<ListCardXref> listCardsToChange=listCardXrefRepository.getListCardWithLowerPriority(listCardXref.getList_id(),listCardXref.getCard_priority_id());
        listCardsToChange.forEach(x->x.setCard_priority_id(x.getCard_priority_id()-1));
        listCardXrefRepository.saveAll(listCardsToChange);
        return true;
    }

    private ListCardXref getByListIdCardId(ListCardXref listCardXref){
        ListCardXref listCardXrefFound=listCardXrefRepository.getByListCardId(listCardXref.getList_id(),listCardXref.getCard_id());
        if(listCardXrefFound==null)
            throw new ResourceNotFoundException("No card with id : " + listCardXref.getCard_id() + " found in list with id " + listCardXref.getList_id());
        return listCardXrefFound;
    }

    public TrelloFunctionResponse movePriorityWithinList(ListCardXref listCardXref,int newPriority){
        int prevPriority=listCardXref.getCard_priority_id();
        TrelloFunctionResponse trelloFunctionResponse=new TrelloFunctionResponse();
        trelloFunctionResponse.setTimestamp(LocalDateTime.now());
        if(prevPriority==newPriority){
            trelloFunctionResponse.setMessage("no change in priority :"+listCardXref.getCard_id());
        }
        if(prevPriority>newPriority){
            List<ListCardXref> listToUpdate=listCardXrefRepository.getListCardWithPriorityBound(listCardXref.getList_id(),newPriority,prevPriority+1);
            listToUpdate.forEach(x-> x.setCard_priority_id(x.getCard_priority_id()+1));
            listCardXrefRepository.saveAll(listToUpdate);
            trelloFunctionResponse.setMessage("card priority upgraded :"+listCardXref.getCard_id());
        }else if(prevPriority<newPriority){
            List<ListCardXref> listToUpdate=listCardXrefRepository.getListCardWithPriorityBound(listCardXref.getList_id(),prevPriority+1,newPriority);
            listToUpdate.forEach(x-> x.setCard_priority_id(x.getCard_priority_id()-1));
            listCardXrefRepository.saveAll(listToUpdate);
            trelloFunctionResponse.setMessage("card priority downgraded :"+listCardXref.getCard_id());
        }
        return trelloFunctionResponse;
    }
}
