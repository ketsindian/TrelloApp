package com.trello.repository;

import com.trello.model.TList;
import com.trello.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Integer> {
    @Query("select usd from UserSecurity usd where user_id=?1")
    public UserSecurity getUserSecurityByUser_id(int user_id);
}
