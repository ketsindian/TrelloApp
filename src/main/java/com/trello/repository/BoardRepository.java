package com.trello.repository;

import com.trello.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("select bd from Board bd where bd.board_owner_id = ?1")
    public List<Board> getAllBoardsByUserId(int user_id);
}
