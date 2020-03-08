package com.trello.service;

import com.trello.model.Card;
import com.trello.model.ListCardXref;
import com.trello.repository.CardRepository;
import com.trello.repository.ListCardXrefRepository;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public List<Card> getCardByListId(int boardId, int listId) {
        helperService.listExistsByBoardIdListId(boardId, listId);
        List<Card> cards = cardRepository.getCardByListId(listId);
        if (cards.isEmpty())
            throw new ResourceNotFoundException("No card found with list : " + listId);
        return cards;
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
    public Card addCardByListId(int boardId, int listId, Card card) {
        helperService.listExistsByBoardIdListId(boardId, listId);
        Card cardAdded = cardRepository.save(card);
        ListCardXref listCardXref = new ListCardXref();
        listCardXref.setCard_id(cardAdded.getCard_id());
        listCardXref.setList_id(listId);
        ListCardXref listCardXrefAdded = listCardXrefRepository.save(listCardXref);
        return cardAdded;
    }

    @Override
    public TrelloFunctionResponse deleteCardByListIdCardId(ListCardXref listCardXref) {
        helperService.cardExistsByListIdCardId(listCardXref);
        cardRepository.deleteById(listCardXref.getCard_id());
        TrelloFunctionResponse deleteResponse = new TrelloFunctionResponse();
        deleteResponse.setTimestamp(LocalDateTime.now());
        deleteResponse.setMessage("card : " + listCardXref.getCard_id() + " removed successfully from list : " + listCardXref.getList_id());
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


}
