package com.projects.lakesidehotel.controller;

import com.projects.lakesidehotel.exception.UserAlreadyExistsException;
import com.projects.lakesidehotel.model.User;
import com.projects.lakesidehotel.service.iUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
public class AuthController {

    private final iUserService userService;


    @PostMapping("/register-user")
    public ResponseEntity<?> registeredUser(User user){

        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration Successful");
        }catch(UserAlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());

        }
    }

}
