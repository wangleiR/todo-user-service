package com.thoughtworks.userService.security;

import com.thoughtworks.userService.model.User;
import com.thoughtworks.userService.repository.UserRepository;
import com.thoughtworks.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LoginFilter  extends OncePerRequestFilter {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token  = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null){
            Long userId = userService.getUserIdByToken(token);
            if (userId == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            User user = userRepository.findOne(userId);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            user,"",new ArrayList<>()
                    )
            );

        }
        filterChain.doFilter(request,response);

    }
}
