package com.trello.security;

import com.trello.model.AppUser;
import com.trello.model.UserSecurity;
import com.trello.repository.UserRepository;
import com.trello.repository.UserSecurityRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mailId) throws UsernameNotFoundException {
        AppUser applicationUser = userRepository.getAppUserByMailId(mailId);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(mailId);
        }
        Optional<UserSecurity> userSecurity = Optional.ofNullable(userSecurityRepository.getUserSecurityByUser_id(applicationUser.getUser_id()));
        if (userSecurity.isEmpty()) {
            throw new UsernameNotFoundException(mailId);
        }
        return new User(applicationUser.getEmail_id(), userSecurity.get().getPassword(), emptyList());
    }
}