package com.trello.repository;

import com.trello.model.ListCardXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListCardXrefRepository extends JpaRepository<ListCardXref, Integer> {

    @Query("select lcx from ListCardXref lcx where lcx.list_id=?1")
    public List<ListCardXref> getListCardXrefByListId(int listId);

    @Query("select lcx from ListCardXref lcx where lcx.list_id=?1 and lcx.card_priority_id>?2")
    public List<ListCardXref> getListCardWithLowerPriority(int listId,int priority);

    @Query("select lcx from ListCardXref lcx where lcx.list_id=?1 and lcx.card_id=?2")
    public ListCardXref getByListCardId(int listId,int cardId);

}
