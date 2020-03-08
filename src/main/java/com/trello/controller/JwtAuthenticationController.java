package com.trello.controller;

import com.trello.model.AppUser;
import com.trello.model.AppUserRequest;
import com.trello.model.JwtRequest;
import com.trello.model.JwtResponse;
import com.trello.security.JwtTokenUtil;
import com.trello.security.UserDetailsServiceImpl;
import com.trello.service.IUserService;
import com.trello.utils.TrelloAuthenticationException;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final IUserService userService;


    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }


    @PostMapping("/signin")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {
        this.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwtToken(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody @Valid AppUserRequest appUserRequest) {
        AppUser appUser = userService.createUser(appUserRequest);
        TrelloFunctionResponse response = new TrelloFunctionResponse();
        response.setMessage("User Created Successfully with id : " + appUser.getUser_id());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new TrelloAuthenticationException("BAD_CREDENTIALS");
        }
    }
}
