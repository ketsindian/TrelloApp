package com.trello.service;

import com.trello.model.AppUser;
import com.trello.model.AppUserRequest;
import com.trello.model.UserSecurity;
import com.trello.repository.UserRepository;
import com.trello.repository.UserSecurityRepository;
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
        UserSecurity createdUserSecurity = userSecurityRepository.save(userSecurity);
        return createdAppUser;
    }
}
