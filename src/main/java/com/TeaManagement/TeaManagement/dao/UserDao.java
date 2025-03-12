package com.TeaManagement.TeaManagement.dao;

import com.TeaManagement.TeaManagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
