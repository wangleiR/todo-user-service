package com.thoughtworks.userService.controller;

import com.thoughtworks.userService.model.Token;
import com.thoughtworks.userService.model.User;
import com.thoughtworks.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired private UserService userService;


    @PostMapping("/login")
    public Token login(@RequestBody User user){
        return userService.login(user);

    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userService.register(user);
    }

    @GetMapping("/authToken")
    public Map<String,Long> anthToken(){
        return userService.authToken();
    }



}
