package com.trello.service;

import com.trello.model.AppUser;
import com.trello.model.AppUserRequest;
import com.trello.model.UserSecurity;
import com.trello.repository.UserRepository;
import com.trello.repository.UserSecurityRepository;
import com.trello.utils.TrelloAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;

    public UserService(UserRepository userRepository, UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    @Transactional
    public AppUser createUser(AppUserRequest userRequest) {
        AppUser appUser = new AppUser();
        appUser.extractFromUSerRequest(userRequest);
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.extractUserSecurityFormUserRequest(userRequest);
        AppUser createdAppUser = userRepository.save(appUser);
        if (createdAppUser == null)
            throw new TrelloAuthenticationException("Falied to create user");
        userSecurity.setUser_id(createdAppUser.getUser_id());
        UserSecurity createdUserSecurity = userSecurityRepository.save(userSecurity);
        if (createdUserSecurity == null)
            throw new TrelloAuthenticationException("Falied to create user");
        return createdAppUser;
    }

    @Override
    public boolean validateAppUser(AppUserRequest appUserRequest) {
//        Example<AppUser> example = Example.of(appUserRequest, ExampleMatcher.);
//
//        if(userRepository.count(appUserRequest.getEmail_id())!=null)
        return false;
    }
}
