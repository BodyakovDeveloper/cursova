package com.team.managing.service;

import com.team.managing.dao.UserDao;
import com.team.managing.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

import static com.team.managing.constant.ConstantClass.AUTHENTICATION_ROLE_SUFFIX;

@Component
public class UserDetailService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
        return new User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(AUTHENTICATION_ROLE_SUFFIX + userEntity.getRoleEntity()
                .getName().toUpperCase()));
    }
}