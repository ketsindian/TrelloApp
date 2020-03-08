package com.trello.controller;

import com.trello.model.AppUserRequest;
import com.trello.model.UserSecurity;
import com.trello.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("user")
public class UserController {

    private final IUserService userService;
    private final BCryptPasswordEncoder  bCryptPasswordEncoder;

    public UserController(IUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody AppUserRequest user) {
        UserSecurity userSecurity=new UserSecurity();
        userSecurity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.createUser(user);
    }

}
