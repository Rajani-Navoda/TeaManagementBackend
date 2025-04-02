package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dao.RoleDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.entity.Role;
import com.TeaManagement.TeaManagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao  roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerNewUser(User user){
        System.out.println(user);
       Role role = roleDao.findById("User").get();

       Set<Role> roles = new HashSet<>();
       roles.add(role);
       user.setRole(roles);
       user.setEmpPassword(getEncodedPassword(user.getEmpPassword()));
        return userDao.save(user);
    }

    public void initRolesAndUser(){

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role for the system");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setEmpNo("Admin001");
        adminUser.setEmpName("John Doe");
        adminUser.setEmpDepartment("Admin");
        adminUser.setEmpEmail("john.doe@example.com");
        adminUser.setEmpPassword(getEncodedPassword("admin@123"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setEmpNo("Emp001");
//        user.setEmpName("Jane Smith");
//        user.setEmpDepartment("Operations");
//        user.setEmpEmail("jane.smith@example.com");
//        user.setEmpPassword(getEncodedPassword("user@123"));
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);

    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
