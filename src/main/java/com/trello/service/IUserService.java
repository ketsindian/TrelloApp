package com.trello.service;

import com.trello.model.AppUser;
import com.trello.model.AppUserRequest;

public interface IUserService {

    public AppUser createUser(AppUserRequest userRequest);
}
