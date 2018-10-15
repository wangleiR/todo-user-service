package com.thoughtworks.userService.service;

import com.thoughtworks.userService.model.Token;
import com.thoughtworks.userService.model.User;
import com.thoughtworks.userService.repository.UserRepository;
import com.thoughtworks.userService.util.Enctrpt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final byte[] MY_TOKEN = "password".getBytes();
    @Autowired private Enctrpt enctrpt;

    @SneakyThrows
    public void register(User user) {
        user.setPassword(enctrpt.encyrptByMD5(user.getPassword()));
        userRepository.save(user);
    }


    @SneakyThrows
    public Token login(User user) {
        User userfind = userRepository.findByName(user.getName());
        Token token = new Token();

        if (userfind.getPassword().equals(enctrpt.encyrptByMD5(user.getPassword()))){

            String signature = Jwts.builder()
                    .signWith(SignatureAlgorithm.HS512, MY_TOKEN)
                    .claim("userId", userfind.getId())
                    .compact();
            token.setToken(signature);
            return token;

        }

        return null;
    }


    public Long getUserIdByToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(MY_TOKEN)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId",Long.class);
    }


    public Map<String,Long> authToken(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Long> map = new HashMap<>();
        map.put("userId",user.getId());
        return map;
    }
}
