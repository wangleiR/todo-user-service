package com.thoughtworks.userService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired private LoginFilter loginFilter;;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users/login","/users/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(loginFilter, BasicAuthenticationFilter.class);
    }
}
