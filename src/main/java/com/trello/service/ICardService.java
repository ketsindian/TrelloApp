package com.trello.service;

import com.trello.model.Card;
import com.trello.model.ListCardXref;
import com.trello.utils.TrelloFunctionResponse;

import java.util.List;


public interface ICardService {
    public List<Card> getCardByListId(int boardId, int listId);

    public Card getCardByListIdCardId(int boardId, ListCardXref listCardXref);

    public Card addCardByListId(int boardId, int listId, Card card);

    public TrelloFunctionResponse deleteCardByListIdCardId(ListCardXref listCardXref);

    public Card updateCardByListIdCardId(int boardId, int listId, Card card);

}
