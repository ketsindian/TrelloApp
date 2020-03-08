package com.trello.repository;

import com.trello.model.ListCardXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListCardXrefRepository extends JpaRepository<ListCardXref, Integer> {
}
