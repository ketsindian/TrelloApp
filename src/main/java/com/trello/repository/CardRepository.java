package com.trello.repository;

import com.trello.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    @Query("select cd from Card cd inner join ListCardXref lcx on lcx.card_id = cd.card_id where lcx.list_id = ?1")
    public List<Card> getCardByListId(int listId);

    @Query("select cd from Card cd inner join ListCardXref lcx on lcx.card_id = cd.card_id where lcx.list_id = ?1 and lcx.card_id= ?2")
    public Card getCardByListIdCardId(int listId, int cardId);
}
