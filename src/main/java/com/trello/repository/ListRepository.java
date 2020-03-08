package com.trello.repository;

import com.trello.model.TList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<TList, Integer> {

    @Query("select ld from TList ld  inner join BoardListXref blx on blx.list_id =ld.list_id where blx.board_id = ?1")
    public List<TList> getListByBoardId(int boardId);

    @Query("select ld from TList ld  inner join BoardListXref blx on blx.list_id =ld.list_id where blx.board_id = ?1 and ld.list_id= ?2")
    public TList getListByBoardIdListId(int boardId, int listId);

}
