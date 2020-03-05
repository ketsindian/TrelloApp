package com.trello.repository;

import com.trello.model.BoardListXref;
import com.trello.model.TList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListXrefRepository extends JpaRepository<BoardListXref, Integer>{

}
