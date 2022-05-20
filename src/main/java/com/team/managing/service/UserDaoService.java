package com.team.managing.service;

import com.team.managing.dao.UserDao;
import com.team.managing.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDaoService {
    private final UserDao userDao;
    private final RoleDaoService roleDaoService;
    private final PasswordEncoder passwordEncoder;

    public UserDaoService(UserDao userDao, RoleDaoService roleDaoService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDaoService = roleDaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean isUserExists(String login, String email) {
        return userDao.isUserExists(login, email);
    }

    @Transactional
    public void create(UserEntity userEntity) {
        userDao.create(userEntity);
    }

    @Transactional
    public void update(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    @Transactional
    public void remove(UserEntity userEntity) {
        userDao.remove(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userDao.findAllUsers();
    }

    public UserEntity setFieldsUserToEdit(UserEntity userFromDb, UserEntity userFromHtmlForm, String roleName) {

        if (!userFromHtmlForm.getPassword().equals("")) {
            userFromHtmlForm.setPassword(passwordEncoder.encode(userFromHtmlForm.getPassword()));
        } else {
            userFromHtmlForm.setPassword(passwordEncoder.encode(userFromDb.getPassword()));
        }

        userFromHtmlForm.setId(userFromDb.getId());
        userFromHtmlForm.setRoleEntity(roleDaoService.getRoleByName(roleName));

        return userFromHtmlForm;
    }
}