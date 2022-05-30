package com.team.managing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.team.managing.constant.ConstantClass.ROLE_ADMIN;
import static com.team.managing.constant.ConstantClass.ROLE_USER;
import static com.team.managing.constant.ConstantClass.ROOT_ADMIN_PAGES;
import static com.team.managing.constant.ConstantClass.ROOT_LOGIN_PAGE;
import static com.team.managing.constant.ConstantClass.ROOT_REDIRECT_PAGE;
import static com.team.managing.constant.ConstantClass.ROOT_SIGNUP_PAGE;
import static com.team.managing.constant.ConstantClass.ROOT_USER_PAGES;

@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .cors().disable()
                    .httpBasic().disable()
                .authorizeRequests()
                    .antMatchers(ROOT_SIGNUP_PAGE).permitAll()
                    .antMatchers(ROOT_USER_PAGES).hasRole(ROLE_USER)
                    .antMatchers(ROOT_ADMIN_PAGES).hasRole(ROLE_ADMIN)
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage(ROOT_LOGIN_PAGE).permitAll()
                    .defaultSuccessUrl(ROOT_REDIRECT_PAGE, true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}