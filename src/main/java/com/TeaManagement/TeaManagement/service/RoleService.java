package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dao.RoleDao;
import com.TeaManagement.TeaManagement.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role){
        return roleDao.save(role);
    }
}
