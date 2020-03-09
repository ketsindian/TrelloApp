package com.trello.repository;

import com.trello.model.BoardListXref;
import com.trello.model.BoardUserXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardUserXrefRepository extends JpaRepository<BoardUserXref, Integer> {

//    @Query("delete from BoardUserXref where board_id=?1 and primary_user_id=?2 and secondary_user_id=?3")
//    public void deleteBoardUserXref(int boardId,int primaryUserId , int secondaryUserId);
}
