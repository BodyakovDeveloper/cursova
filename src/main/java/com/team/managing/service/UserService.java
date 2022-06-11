package com.team.managing.service;

import com.team.managing.dao.UserDao;
import com.team.managing.entity.UserEntity;
import com.team.managing.exception.UserValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Transactional
    public boolean isUserExists(String login, String email) {
        return userDao.isUserExists(login, email);
    }

    @Transactional
    public void create(UserEntity user) throws UserValidationException {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!validator.isUserValid(user)) {
            log.warn("not valid fields: " + validator.getWrongFieldName());
            throw new UserValidationException("not valid fields :" + validator.getWrongFieldName());
        }
        userDao.create(user);
    }

    @Transactional
    public void update(UserEntity user) throws UserValidationException {

        if (!validator.isUserValid(user)) {
            log.warn("not valid fields: " + validator.getWrongFieldName());
            throw new UserValidationException("not valid fields :" + validator.getWrongFieldName());
        }
        userDao.update(user);
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
        userFromHtmlForm.setRoleEntity(roleService.getRoleByName(roleName));

        return userFromHtmlForm;
    }
}