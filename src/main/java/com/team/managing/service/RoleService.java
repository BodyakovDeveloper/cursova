package com.team.managing.service;

import com.team.managing.entity.RoleEntity;
import com.team.managing.dao.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    @Transactional(readOnly = true)
    public List<RoleEntity> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Transactional(readOnly = true)
    public RoleEntity getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}