package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
