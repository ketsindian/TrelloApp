package com.trello.repository;

import com.trello.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    @Query("select u from AppUser u where email_id = ?1")
    public AppUser getAppUserByMailId(String email_id);
}
