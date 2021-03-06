package com.team.managing.dao;

import com.team.managing.entity.RoleEntity;

import java.util.List;

public interface RoleDao extends Dao<RoleEntity> {

    List<RoleEntity> findAllRoles();

    RoleEntity getRoleByName(String name);
}