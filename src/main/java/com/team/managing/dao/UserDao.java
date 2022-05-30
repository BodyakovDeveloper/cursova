package com.team.managing.dao;

import com.team.managing.entity.UserEntity;

import java.util.List;

public interface UserDao extends Dao<UserEntity> {

    List<UserEntity> findAllUsers();

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String email);

    boolean isUserExists(String login, String email);
}